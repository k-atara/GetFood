package mx.tec.getfood.ui.QR.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_historial.view.*
import kotlinx.android.synthetic.main.layout_header_history.view.*
import mx.tec.getfood.R
import mx.tec.getfood.ui.QR.Adapters.ScannedResultListAdapter
import mx.tec.getfood.ui.QR.DBHelper
import mx.tec.getfood.ui.QR.HelperDB
import mx.tec.getfood.ui.QR.database.QrResultDataBase
import mx.tec.getfood.ui.QR.entities.QrResults
import mx.tec.getfood.ui.QR.utils.gone
import mx.tec.getfood.ui.QR.utils.visible


class Historial : Fragment() {
    enum class ResultListType{
        ALL_RESULTS,FAV_RESULTS
    }

    companion object{
        private const val ARGUMENT_RESULT_LIST_TYPE="ArgumentResultListType"
        private lateinit var mView:View
        private lateinit var dbHelper: DBHelper
        fun newInstance(screenType: ResultListType): Historial {
            val bundle=Bundle()
            bundle.putSerializable(ARGUMENT_RESULT_LIST_TYPE, screenType)
            val fragment=Historial()
            fragment.arguments=bundle
            return fragment
        }
    }
    private lateinit var resultType:ResultListType
    //Video 7
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleArguments()
    }
    private fun handleArguments() {
        resultType=requireArguments().getSerializable(ARGUMENT_RESULT_LIST_TYPE) as ResultListType
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView= inflater.inflate(R.layout.fragment_historial, container, false)
        init()
        showListOfResults()
        setSwipeRefreshLayout()
        return mView
    }

    private fun setSwipeRefreshLayout() {
        mView.swipeRefresh.setOnRefreshListener {
            mView.swipeRefresh.isRefreshing=false
            showListOfResults()
        }
    }

    private fun init() {
        dbHelper= HelperDB(QrResultDataBase.getAppDatabase(requireContext())!!)
    }
    private fun showListOfResults() {
        when(resultType){
            ResultListType.ALL_RESULTS->{
                showAllResults()
            }
            ResultListType.FAV_RESULTS->{
                showAllFav()
            }
        }
    }

    private fun showAllResults() {
        var listOfAllResults = dbHelper.getAllQRScannedResult()
        showResults(listOfAllResults)
        mView.tvHeaderText.text="Historial"
    }

    private fun showAllFav() {
        var listOfFavortiteResults = dbHelper.getAllFavouriteQRScannedResult()
        showResults(listOfFavortiteResults)
    }

    private fun showResults(listQrResults: List<QrResults>) {
        if(listQrResults.isEmpty()){
            showEmptyScreen()
        }else{
            InitRecyclerView(listQrResults)
        }
    }

    private fun InitRecyclerView(listQrResults: List<QrResults>) {
        mView.scannedHistoryRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        mView.scannedHistoryRecyclerView.adapter=
            ScannedResultListAdapter(dbHelper,requireContext(),listQrResults.toMutableList())
    }

    private fun showEmptyScreen() {
        mView.scannedHistoryRecyclerView.gone()
        mView.noResultFound.visible()
    }

}
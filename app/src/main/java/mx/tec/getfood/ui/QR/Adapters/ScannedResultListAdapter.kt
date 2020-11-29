package mx.tec.getfood.ui.QR.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_item_result.view.*
import mx.tec.getfood.R
import mx.tec.getfood.ui.QR.DBHelper
import mx.tec.getfood.ui.QR.dialogs.QRCodeResultDialog
import mx.tec.getfood.ui.QR.entities.QrResults
import mx.tec.getfood.ui.QR.utils.toFormattedDisplay

class ScannedResultListAdapter( var dbHelperI: DBHelper,
                                var context: Context,
                                private var listOfScannedResult: MutableList<QrResults>
) : RecyclerView.Adapter<ScannedResultListAdapter.ScannedResultListViewHolder>() {
    private var resultDialog= QRCodeResultDialog(context)
    inner class ScannedResultListViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        fun bind(qrResult: QrResults, position: Int) {
            view.result.text=qrResult.result
            view.tvTime.text=qrResult.calendar.toFormattedDisplay()
            onClicks(qrResult)
        }

        private fun onClicks(qrResult: QrResults) {
            view.setOnClickListener{
                resultDialog.show(qrResult)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScannedResultListViewHolder {
        return ScannedResultListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.single_item_result,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ScannedResultListViewHolder, position: Int) {
        holder.bind(listOfScannedResult[position],position)
    }

    override fun getItemCount(): Int {
        return listOfScannedResult.size
    }

}
//package com.xcod33.risfund
//
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageButton
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//
//class CustomAdapterPaketData(private val list: List<ItemsViewModelPaketData>) :
//    RecyclerView.Adapter<CustomAdapterPaketData.ViewHolder>() {
//
//    /**
//     * Provide a reference to the type of views that you are using
//     * (custom ViewHolder).
//     */
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val purchaseTextView: TextView = itemView.findViewById(R.id.purchaseTextView)
//        val hargaPurchaseTextView: TextView = itemView.findViewById(R.id.hargaPurchaseTextView)
//        val purchaseImageButton: ImageButton = itemView.findViewById(R.id.purchaseImageButton)
//        val linePurchaseImageView: ImageView = itemView.findViewById(R.id.linePurchaseImageView)
//        val layout1: LinearLayout = itemView.findViewById(R.id.layout1)
//
////        init {
////            // Define click listener for the ViewHolder's View.
////            purchaseTextView = view.findViewById(R.id.purchaseTextView)
////            hargaPurchaseTextView = view.findViewById(R.id.hargaPurchaseTextView)
////            purchaseImageButton = view.findViewById(R.id.purchaseImageButton)
////            linePurchaseImageView = view.findViewById(R.id.linePurchaseImageView)
////        }
//    }
//
//    // Create new views (invoked by the layout manager)
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        // Create a new view, which defines the UI of the list item
//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.card_view_purchases, viewGroup, false)
//
//        return ViewHolder(view)
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        val itemsViewModelPaketData: list[position] //masih error di list[position]
//
//        // Get element from your dataset at this position and replace the
//        // contents of the view with that element
//        viewHolder.purchaseTextView.text = itemsViewModelPaketData.getTitle
//        viewHolder.hargaPurchaseTextView.text = itemsViewModelPaketData.getTitle
//        viewHolder.purchaseImageButton.setImageResource(itemsViewModelPaketData.getImage)
//        viewHolder.linePurchaseImageView.setImageResource(itemsViewModelPaketData.getLineImage)
//
//        viewHolder.layout1.setOnClickListener{
//            val intent = Intent(viewHolder.itemView.context, PaymentConfirmationActivity::class.java)
//            intent.putExtra("title", itemsViewModelPaketData.getTitle)
//            intent.putExtra("description", itemsViewModelPaketData.getDescription)
//            viewHolder.itemView.context.startActivity(intent)
//        }
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    override fun getItemCount(): Int = list.size
//
//}

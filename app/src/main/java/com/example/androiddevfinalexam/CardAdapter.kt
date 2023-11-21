import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddevfinalexam.CardData
import com.example.androiddevfinalexam.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException
import com.squareup.picasso.Picasso

class CardAdapter(private val cardDataList: List<CardData>) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.productcard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardData = cardDataList[position]

        // Bind data to views
//        holder.imageView.
        holder.textViewName.text = cardData.name
        holder.textViewDesc.text = cardData.description
        holder.textViewPrice.text = "₱ "+cardData.price.toString()

        loadImageFromUrl(cardData.photoUrl, holder.imageView)

    }

    override fun getItemCount(): Int {
        return cardDataList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.prodPic)
        val textViewName: TextView = itemView.findViewById(R.id.prodName)
        val textViewDesc: TextView = itemView.findViewById(R.id.prodDesc)
        val textViewPrice: TextView = itemView.findViewById(R.id.prodPrice)
    }

    private fun loadImageFromUrl(photoUrl: String?, imageView: ImageView) {
        Picasso.get().load(photoUrl).into(imageView)
    }


}

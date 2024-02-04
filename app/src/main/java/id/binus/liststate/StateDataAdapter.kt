package id.binus.liststate

import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class StateDataAdapter(
    private val stateDataList: List<ResultDt>,
    private val context: Context,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<StateDataAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(stateData: ResultDt)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stateTextView: TextView = itemView.findViewById(R.id.stateTextView)
        val populationTextView: TextView = itemView.findViewById(R.id.populationTextView)
        val imageView: ImageView = itemView.findViewById(R.id.stateImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stateData = stateDataList[position]

        // Bind data to views
        holder.stateTextView.text = stateData.State
        holder.populationTextView.text = "Population: ${stateData.Population}"
        val imageUrl = "https://source.unsplash.com/random/?city,${stateData.State}";
        Picasso.get().load(imageUrl).into(holder.imageView);

        // Set click listener
        holder.itemView.setOnClickListener {
            // Handle item click
            itemClickListener.onItemClick(stateData)
        }
    }

    override fun getItemCount(): Int {
        return stateDataList.size
    }
}

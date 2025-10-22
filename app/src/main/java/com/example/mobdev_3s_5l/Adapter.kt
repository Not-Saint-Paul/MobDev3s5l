package com.example.mobdev_3s_5l

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter(private val photoList: List<Photo>?) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    private val safePhotoList: List<Photo> = photoList ?: emptyList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = safePhotoList[position]

        if (photo.id.isNullOrEmpty() || photo.server.isNullOrEmpty() || photo.secret.isNullOrEmpty()) {
            Glide.with(holder.itemView.context).clear(holder.imageView)
            holder.itemView.setOnClickListener(null)
            return
        }

        val url = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_z.jpg"

        Glide.with(holder.itemView.context)
            .load(url)
            .centerCrop()
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Photo URL", url)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "Ссылка скопирована", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = safePhotoList.size
}
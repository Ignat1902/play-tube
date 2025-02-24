package com.example.playtube.features.videolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.playtube.R
import com.example.playtube.entities.VideoInfoUI
import com.example.playtube.utils.formatSecondsToTime

class VideoListAdapter(val listener: Listener) :
    RecyclerView.Adapter<VideoListAdapter.VideoListItemViewHolder>() {

    private var videoList: List<VideoInfoUI> = emptyList()
        set(value) {
            val callback = CommonCallbackImpl(
                field,
                value,
                areItemsTheSame = { oldItem, newItem -> oldItem.id != newItem.id }
            )
            field = value
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
        }

    class VideoListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTV = itemView.findViewById<TextView>(R.id.title_text_view)
        private val thumbnailIV = itemView.findViewById<ImageView>(R.id.thumbnail_image_view)
        private val durationTv = itemView.findViewById<TextView>(R.id.duration_text_view)

        fun onBind(data: VideoInfoUI) {
            with(data) {
                titleTV.text = title
                Glide.with(itemView.rootView.context)
                    .load(thumbnailUrl)
                    .transform(CenterCrop())
                    .into(thumbnailIV)

                durationTv.text = formatSecondsToTime(duration)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_video_card, parent, false)
        return VideoListItemViewHolder(view)
    }

    override fun getItemCount(): Int = videoList.size


    override fun onBindViewHolder(holder: VideoListItemViewHolder, position: Int) {
        holder.onBind(videoList[position])
        holder.itemView.setOnClickListener {
            listener.clickToVideo(videoList[position].videoUrl)
        }
    }

    fun updateList(newList: List<VideoInfoUI>) {
        videoList = newList
    }
}

interface Listener {
    fun clickToVideo(videoUrl: String)
}
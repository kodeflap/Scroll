package kodeflap.com.scroll.ui.adapter

import User
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kodeflap.com.scroll.R
import kodeflap.com.scroll.utils.show

class CustomAdapterMovies :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    private var userList = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == VIEW_TYPE_ITEM) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_user, parent, false)
            UserViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.lazy_loading, parent, false)
            LoadingViewHolder(view)
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is UserViewHolder) {
            // Pass the individual User object, not a list
            holder.bindItems(userList[position])
        } else if (holder is LoadingViewHolder) {
            holder.showLoadingView()
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (userList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun setData(newUserList: List<User>?) {
        if (newUserList != null) {
            if (userList.isNotEmpty())
                userList.removeAt(userList.size - 1)
            userList.clear()
            userList.addAll(newUserList)
            notifyDataSetChanged()
        } else {
            if (newUserList != null) {
                userList.add(newUserList)
            }
        }
    }
    fun getData() = userList

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val userImage: ImageView = itemView.findViewById(R.id.image)
        private val textName: TextView = itemView.findViewById(R.id.text_name)
        private val textEmail: TextView = itemView.findViewById(R.id.text_email)

        @SuppressLint("SetTextI18n")
        fun bindItems(users: User) {
            textName.text = users.firstName
            textEmail.text = users.email
            Glide.with(userImage.context).load(users.image)
                .centerCrop()
                .thumbnail(0.5f)
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(userImage)
        }

    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)

        fun showLoadingView() {
            progressBar.show()
        }
    }

}
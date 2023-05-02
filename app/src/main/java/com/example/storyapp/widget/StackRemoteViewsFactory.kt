package com.example.storyapp.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import androidx.room.Room
import com.example.storyapp.R
import com.example.storyapp.database.StoryApp
import com.example.storyapp.database.StoryAppDatabase
import timber.log.Timber
import java.net.URL

internal class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Bitmap>()
    private var stories: MutableList<StoryApp> = mutableListOf()
//    private val db: AppDatabase? = null

    companion object {
        const val DB_NAME = "story_database"
    }

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
//        val database = Room.databaseBuilder(
//            mContext.applicationContext, StoryAppDatabase::class.java,
//            DB_NAME
//        ).build()
//        database.StoryAppDao().getAllStories().forEach {
//            stories.add(
//                StoryApp(
//                    it.id,
//                    it.photoUrl
//                )
//            )
//        }

        val database = Room.databaseBuilder(
            mContext.applicationContext, StoryAppDatabase::class.java,
            DB_NAME
        ).build()
        val storyAppDao = database.StoryAppDao()
        val storyApps = storyAppDao.getAllStories()

        // Tambahkan setiap StoryApp ke list stories
        for (StoryApp in storyApps) {
            stories.add(StoryApp)
        }
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = stories.size

    override fun getViewAt(position: Int): RemoteViews {
//        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
//        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])
//
//        val extras = bundleOf(
//            ImagesBannerWidget.EXTRA_ITEM to position
//        )
//        val fillInIntent = Intent()
//        fillInIntent.putExtras(extras)
//
//        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
//        return rv

        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imgStory, urlToBitmap(stories[position].photoUrl))

        return rv
    }

    private fun urlToBitmap(src: String): Bitmap? {
        return try {
            val url = URL(src)
            val connection = url.openConnection()
            connection.doInput = true
            connection.connect()
            val input = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (ex: Exception) {
            Timber.e(ex.message.toString())
            null
        }
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}
package com.example.drawerkotlin

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {
    var menuIcon: ImageView? = null
    var contentView: LinearLayout? = null

    //    drawer menu
    var drawerLayout: DrawerLayout? = null
    lateinit var navigationView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Hooks
        menuIcon = findViewById(R.id.menu_icon)
        contentView = findViewById(R.id.content)


//        menu hooks
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById<Any>(R.id.navigation_view)
        navigationDrawer()
    }

    //Navigation drawer functions
    private fun navigationDrawer() {

        //        Navigation drawer
        navigationView?.bringToFront()
        navigationView?.setNavigationItemSelectedListener(this)
        navigationView?.setCheckedItem(R.id.nav_home)
        menuIcon!!.setOnClickListener { if (drawerLayout!!.isDrawerVisible(GravityCompat.START)) drawerLayout!!.closeDrawer(GravityCompat.START) else drawerLayout!!.openDrawer(GravityCompat.START) }
        animateNavigationDrawer()
    }

    private fun animateNavigationDrawer() {
        drawerLayout!!.setScrimColor(resources.getColor(R.color.colorAccent))
        drawerLayout!!.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                //Scale the view based on current slide offset
                val diffScaledOffset = slideOffset * (1 - END_SCALE)
                val offsetScale = 1 - diffScaledOffset
                contentView!!.scaleX = offsetScale
                contentView!!.scaleY = offsetScale

                //Translate the view,accounting for the scaled view
                val xOffset = drawerView.width * slideOffset
                val xOffsetDiff = contentView!!.width * diffScaledOffset / 2
                val xTranslation = xOffset - xOffsetDiff
                contentView!!.translationX = xTranslation
            }

            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerVisible(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
    }

    fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

    companion object {
        //    variables
        const val END_SCALE = 0.7f
    }
}
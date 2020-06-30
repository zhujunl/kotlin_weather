package com.example.zjl.kotlin_weather.ui.place



//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.RecyclerView
//import com.example.zjl.kotlin_weather.R
//import com.example.zjl.kotlin_weather.logic.model.Place
//import kotlinx.android.synthetic.main.place_item.view.*
//
//class PlaceAdapter (private val fragment: Fragment, private val placeList:List<Place>):
//        RecyclerView.Adapter<PlaceAdapter.ViewHolder>(){
//    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
//        val placeName:TextView=view.findViewById(R.id.PlaceName)
//        val placeaddress:TextView=view.findViewById(R.id.PlaceAddress)
//    }
//
//    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
//        val view =LayoutInflater.from(p0.context).inflate(R.layout.place_item,p0,false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
//        val place=placeList[p1]
//        p0.placeName.text=place.name
//        p0.placeaddress.text=place.address
//    }
//
//    override fun getItemCount(): Int {
//        return placeList.size
//    }
//}





import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zjl.kotlin_weather.R
import com.example.zjl.kotlin_weather.logic.model.Place
import com.example.zjl.kotlin_weather.ui.weather.WeatherActivity

class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>) :
        RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.PlaceName)
        val placeAddress: TextView = view.findViewById(R.id.PlaceAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)
        val holder=ViewHolder(view)
        holder.itemView.setOnClickListener{
            val position=holder.adapterPosition
            val place=placeList[position]
            val intent= Intent(parent.context,WeatherActivity::class.java).apply {
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            fragment.viewModel.savePlace(place)
            fragment.startActivity(intent)
            fragment.activity?.finish()
        }
        return holder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

    override fun getItemCount() = placeList.size

}

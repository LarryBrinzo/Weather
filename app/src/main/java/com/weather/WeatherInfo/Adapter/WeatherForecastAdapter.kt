package com.weather.WeatherInfo.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.weather.DataClass.ForecastDay
import com.weather.R
import java.text.SimpleDateFormat


class WeatherForecastAdapter(private val list: List<ForecastDay>, private val context: Context?) :
    RecyclerView.Adapter<WeatherForecastAdapter.MyHoder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHoder {

        val view = LayoutInflater.from(context).inflate(R.layout.forecast_card, parent, false)
        return MyHoder(view)
    }

    override fun onBindViewHolder(holder: MyHoder, @SuppressLint("RecyclerView") position: Int) {

        var day: String = list[position].date

        val df = SimpleDateFormat("yyyy-MM-dd")
        val startDate = df.parse(day)

        val sdf = SimpleDateFormat("EEEE")
        val d = startDate
        val dayOfTheWeek = sdf.format(d)

        holder.day.text=dayOfTheWeek

        var currentTemperature=list[position].day.avgtemp_c
        currentTemperature=currentTemperature!!.substring(0, currentTemperature.indexOf('.'))
        currentTemperature="$currentTemperature°"

        holder.temp.text=currentTemperature

    }

    override fun getItemCount(): Int {
        var arr = 0
        try { if (list.size == 0) { arr = 0 }
            else { arr = list.size } }
        catch (ignored: Exception) { }
        return arr
    }

    inner class MyHoder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var temp: TextView
        var day: TextView

        init {
            temp = itemView.findViewById(R.id.temp)
            day = itemView.findViewById(R.id.day)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}


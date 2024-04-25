package kr.ac.kopo.reservation2024

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.CalendarView
import android.widget.Chronometer
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.TimePicker

class MainActivity : AppCompatActivity() {
    lateinit var chrono : Chronometer
    lateinit var rg : RadioGroup
    lateinit var calendar : CalendarView
    lateinit var timePick : TimePicker
    lateinit var textResult : TextView
    var selectedYear : Int = 0
    var selectedMonth : Int = 0
    var selectedDay : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chrono = findViewById<Chronometer>(R.id.chrono)
        rg = findViewById<RadioGroup>(R.id.rg)
        calendar = findViewById<CalendarView>(R.id.calendar)
        timePick = findViewById<TimePicker>(R.id.timePick)
        textResult = findViewById<TextView>(R.id.textResult)

        calendar.visibility = View.INVISIBLE
        timePick.visibility = View.INVISIBLE

        rg.setOnCheckedChangeListener(rgListener)

        chrono.setOnClickListener {
            chrono.base = SystemClock.elapsedRealtime()
            chrono.start()
            chrono.setTextColor(Color.MAGENTA)
            rg.visibility = View.VISIBLE
        }

        textResult.setOnLongClickListener {
            chrono.stop()
            chrono.setTextColor(Color.CYAN)
            selectedYear = getYearFromCalendar()
            selectedMonth = getMonthFromCalendar()
            selectedDay = getDayFromCalendar()

            val selectedHour = timePick.currentHour
            val selectedMinute = timePick.currentMinute

            textResult.setText("" + selectedYear + "년" +(selectedMonth+1) + "월" + selectedDay + "일")
            textResult.setText("" + timePick.currentHour+"시 ")
            textResult.setText("" + timePick.currentMinute + "분 ")
            textResult.append(" 예약 완료")

            rg.visibility = View.INVISIBLE
            calendar.visibility = View.INVISIBLE
            timePick.visibility = View.INVISIBLE

            return@setOnLongClickListener true
        }

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            selectedYear = year
            selectedMonth = month
            selectedDay = dayOfMonth
        }
    }

    private fun getYearFromCalendar(): Int {
        val calendarDate = java.util.Calendar.getInstance()
        calendarDate.timeInMillis = calendar.date
        return calendarDate.get(java.util.Calendar.YEAR)
    }

    private fun getMonthFromCalendar(): Int {
        val calendarDate = java.util.Calendar.getInstance()
        calendarDate.timeInMillis = calendar.date
        return calendarDate.get(java.util.Calendar.MONTH)
    }

    private fun getDayFromCalendar(): Int {
        val calendarDate = java.util.Calendar.getInstance()
        calendarDate.timeInMillis = calendar.date
        return calendarDate.get(java.util.Calendar.DAY_OF_MONTH)
    }

    var rgListener = RadioGroup.OnCheckedChangeListener { group, CheckedId ->
        calendar.visibility = View.INVISIBLE
        timePick.visibility = View.INVISIBLE
        when (rg.checkedRadioButtonId) {
            R.id.rbDate -> calendar.visibility = View.VISIBLE
            R.id.rbTime -> timePick.visibility = View.VISIBLE
        }
    }
}

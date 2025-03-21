package com.example.tippy

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tippy.databinding.ActivityMainBinding

private const val TAG: String = "MainActivity"
private const val INITIAL_TIP_PERCENT: Int = 0

class MainActivity : AppCompatActivity() {
    private lateinit var seekBarTip: SeekBar

    private lateinit var txtBase: EditText
    private lateinit var lblTipPercent: TextView
    private lateinit var lblTipAmount: TextView
    private lateinit var lblTotal: TextView
    private lateinit var lblTipStatus: TextView

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
        setContentView(binding.root)
        init()
        initVars()
        seekBarTip.progress = INITIAL_TIP_PERCENT
        lblTipPercent.text = "$INITIAL_TIP_PERCENT%"
        updateTipStatus(INITIAL_TIP_PERCENT)

        seekBarTipAction()
        txtBaseAction()

    }

    private fun txtBaseAction() {
        txtBase.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                computeTipAndTotalAmount()
            }

        })

    }

    private fun computeTipAndTotalAmount() {
        if (txtBase.text.isEmpty()){
            lblTipAmount.text = ""
            lblTotal.text = ""
            return
        }
        val baseAmount: Double = txtBase.text.toString().toDouble()
        val tipPercent = seekBarTip.progress
        val tipAmount = calcPercentage(baseAmount, tipPercent)
        val totalAmount = baseAmount + tipAmount


        lblTipAmount.text = to2dp(tipAmount)
        lblTotal.text = to2dp(totalAmount)



    }


    private fun seekBarTipAction() {
        seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                lblTipPercent.text = "$progress%"
                computeTipAndTotalAmount()
                updateTipStatus(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

    }
    private fun updateTipStatus(tipPercentage: Int){
        val tipDescription = when(tipPercentage){
            in 0..9 -> "Poor"
            in 10..14 -> "Acceptable"
            in 15..19 -> "Good"
            in 20..24 -> "Great"
            else -> "Amazing"

        }
        lblTipStatus.text = tipDescription
        val fraction = tipPercentage.toFloat() / seekBarTip.max
        val worstColour = getColour(R.color.colour_worst)
        val bestColour = getColour(R.color.colour_best)

        val  colour = ArgbEvaluator().evaluate(fraction, worstColour, bestColour) as Int
        lblTipStatus.setTextColor(colour)
    }
    private fun getColour(colour:Int): Int {
        return ContextCompat.getColor(this, colour)
    }

    private fun initVars() {
        txtBase = binding.txtBaseAmount;
        seekBarTip = binding.seekBarTip
        lblTipPercent = binding.lblTipPercent
        lblTipAmount = binding.lblTipAmount
        lblTotal = binding.lblTotalAmount
        lblTipStatus = binding.lblTipStatus

    }

    private fun init() {


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

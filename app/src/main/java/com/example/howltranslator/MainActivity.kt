package com.example.howltranslator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.howltranslator.databinding.ActivityMainBinding
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import java.util.Calendar.getInstance

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var option : FirebaseTranslatorOptions.Builder? = null
    var translater : FirebaseTranslator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        option = FirebaseTranslatorOptions.Builder()   // 번역할 언어 설정
            //.setSourceLanguage(FirebaseTranslateLanguage.EN)
            //.setTargetLanguage(FirebaseTranslateLanguage.KO)
//        translater = FirebaseNaturalLanguage.getInstance().getTranslator(option!!.build())    // 번역 해 주는 코드
//        translater?.downloadModelIfNeeded()     // option 에 단어를 넣어 주고 번역 할 언어를 다운받아야 함

        binding.inputText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                translater?.translate(p0.toString())?.addOnSuccessListener {
                    binding.outTextView.text = it   // 강의에는 binding.outText.text = it      이라고 되어 있으나 이러면 안됨
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }


        })
        // 왼쪽 스피너에 이벤트
        binding.fromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0){
                    // 영어
                    option?.setSourceLanguage(FirebaseTranslateLanguage.EN)

                } else if (p2 == 1){
                    // 한국어
                    option?.setSourceLanguage(FirebaseTranslateLanguage.KO)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        binding.toSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0){
                    // 영어
                    option?.setTargetLanguage(FirebaseTranslateLanguage.EN)

                } else if (p2 == 1){
                    // 한국어
                    option?.setTargetLanguage(FirebaseTranslateLanguage.KO)
                }
                translater = FirebaseNaturalLanguage.getInstance().getTranslator(option!!.build())    // 번역 해 주는 코드
                translater?.downloadModelIfNeeded()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        }
    }
}
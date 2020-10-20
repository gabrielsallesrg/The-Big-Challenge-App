package com.gsrg.tbc.stepcounter

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.gsrg.tbc.core.utils.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class StepCounter(private val context: Context) : IStepCounter {

    private var fitnessOptions: FitnessOptions? = null
    private val stepCountDataType = DataType.TYPE_STEP_COUNT_DELTA
    private val distanceDataType = DataType.TYPE_DISTANCE_DELTA

    override fun steps(): Flow<Int> = flow {
        val steps = suspendCoroutine<Int> {
            Fitness.getHistoryClient(context, GoogleSignIn.getAccountForExtension(context, fitnessOptions()))
                .readDailyTotal(stepCountDataType)
                .addOnSuccessListener { dataSet: DataSet ->
                    val totalSteps: Int = if (dataSet.isEmpty) 0 else dataSet.dataPoints[0].getValue(Field.FIELD_STEPS).asInt()
                    Timber.tag(TAG()).d("Steps: $totalSteps")
                    it.resume(totalSteps)
                }
                .addOnFailureListener {
                    Timber.tag(TAG()).e("There was a problem getting steps: ${it.localizedMessage}")
                }
        }
        emit(steps)
    }

    override fun distanceInMeters(): Flow<Int> = flow {
        val steps = suspendCoroutine<Int> {
            Fitness.getHistoryClient(context, GoogleSignIn.getAccountForExtension(context, fitnessOptions()))
                .readDailyTotal(distanceDataType)
                .addOnSuccessListener { dataSet: DataSet ->
                    val totalSteps: Int = if (dataSet.isEmpty) 0 else dataSet.dataPoints[0].getValue(Field.FIELD_DISTANCE).asFloat().toInt()
                    Timber.tag(TAG()).d("Steps: $totalSteps")
                    it.resume(totalSteps)
                }
                .addOnFailureListener {
                    Timber.tag(TAG()).e("There was a problem getting steps: ${it.localizedMessage}")
                }
        }
        emit(steps)
    }

    override fun fitnessOptions(): GoogleSignInOptionsExtension {
        if (fitnessOptions == null) {
            fitnessOptions = FitnessOptions.builder()
                .addDataType(stepCountDataType, FitnessOptions.ACCESS_READ)
                .addDataType(distanceDataType, FitnessOptions.ACCESS_READ)
                .build()
        }
        return fitnessOptions!!
    }
}
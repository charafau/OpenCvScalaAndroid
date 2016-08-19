package com.nullpointerbay.opencvscala

import android.app.Activity
import android.os.Bundle
import org.opencv.android._
import android.util.Log
import android.view.View
import org.opencv.android.CameraBridgeViewBase.{CvCameraViewFrame, CvCameraViewListener2}
import org.opencv.core.{Core, CvType, Mat}
import org.opencv.imgproc.Imgproc

class MainActivity extends Activity with TypedFindView
  with CvCameraViewListener2 {
  lazy val textview = findView(TR.text)
  lazy val openCvCameraView = findViewById(R.id.HelloOpenCvView).asInstanceOf[CameraBridgeViewBase]

  var rgba: Mat = _
  var rgbaF: Mat = _
  var rgbaT: Mat = _


  val TAG = "MainActivity"
  val loaderCallback = new BaseLoaderCallback(this) {
    override def onManagerConnected(status: Int): Unit = {

      status match {
        case LoaderCallbackInterface.SUCCESS => {
          Log.i(TAG, "OpenCvLoaded")
          openCvCameraView.enableView()
        }
        case _ => super.onManagerConnected(status)
      }
    }
  }

  /** Called when the activity is first created. */
  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)
    textview.setText("Hello world, from opencvscala")
    openCvCameraView.setVisibility(View.VISIBLE);
    openCvCameraView.setCvCameraViewListener(this);

  }


  override def onPause(): Unit = {
    super.onPause()
    if (openCvCameraView != null) {
      openCvCameraView.disableView()
    }
  }

  override def onResume(): Unit = {
    super.onResume()
    if (!OpenCVLoader.initDebug()) {
      Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
      OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, loaderCallback);
    } else {
      Log.d(TAG, "OpenCV library found inside package. Using it!");
      loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
    }
  }

  override def onDestroy(): Unit = {
    super.onDestroy()
    if (openCvCameraView != null) {
      openCvCameraView.disableView()
    }
  }

  override def onCameraViewStarted(width: Int, height: Int) = {
    rgba = new Mat(height, width, CvType.CV_8UC4)
    rgbaF = new Mat(height, width, CvType.CV_8UC4)
    rgbaT = new Mat(width, width, CvType.CV_8UC4)
  }

  override def onCameraViewStopped() = {}

  override def onCameraFrame(inputFrame: CvCameraViewFrame) = {
    rgba = inputFrame.rgba()
    Core.transpose(rgba, rgbaT)
    Imgproc.resize(rgbaT, rgbaF, rgbaF.size(), 0, 0, 0)
    Core.flip(rgbaF, rgba, 1)

    rgba
  }

}

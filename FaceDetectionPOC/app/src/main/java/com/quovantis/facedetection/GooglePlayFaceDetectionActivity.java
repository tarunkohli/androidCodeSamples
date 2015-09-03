package com.quovantis.facedetection;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

/**
 *
 * @author rahul rastogi
 */
public class GooglePlayFaceDetectionActivity extends AppCompatActivity {

    private ImageView mIvDetected;
    private Bitmap mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_play_face_detection);

        mIvDetected = (ImageView) findViewById(R.id.iv_detected);

        //Load an image
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inMutable=true;
        mImage = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap, opts);

        //Configure FaceDetector
        FaceDetector detector = new FaceDetector.Builder(this)
                .setMode(FaceDetector.ACCURATE_MODE)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setTrackingEnabled(false)
                .build();


        if(false == detector.isOperational()){
            Toast.makeText(this, "Face detection service is not ready", Toast.LENGTH_SHORT).show();
            return;
        }

        //Add the image on a Frame object
        Frame frame = new Frame.Builder()
                .setBitmap(mImage)
                .build();

        //Detect all faces from Frame object
        SparseArray<Face> faceArray = detector.detect(frame);

        //Do some drawing on faces
        Bitmap outBitmap = drawOnFace(faceArray);
        mIvDetected.setImageBitmap(outBitmap);

        //Releasing the detector object
        detector.release();
    }


    /**
     * Method to do some drawing on faces
     */
    private Bitmap drawOnFace(SparseArray<Face> faceArray){
        Bitmap outBitmap = Bitmap.createBitmap(mImage.getWidth(), mImage.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(outBitmap);
        canvas.drawBitmap(mImage, 0, 0, null);

        for(int i=0; i < faceArray.size(); i++){
            Face face = faceArray.get(i);

            //Drawing rectangle on each face
            drawRectangle(canvas, face.getPosition(), face.getWidth(), face.getHeight());

            //Drawing a point on each face features
            for(Landmark landmark : face.getLandmarks()) {
                switch (landmark.getType()){
                    case Landmark.LEFT_EYE:
                        drawPoint(canvas, landmark.getPosition());
                        break;
                    case Landmark.RIGHT_EYE:
                        drawPoint(canvas, landmark.getPosition());
                        break;
                    case Landmark.BOTTOM_MOUTH:
                        drawPoint(canvas, landmark.getPosition());
                        break;
                    case Landmark.LEFT_MOUTH:
                        drawPoint(canvas, landmark.getPosition());
                        break;
                    case Landmark.RIGHT_MOUTH:
                        drawPoint(canvas, landmark.getPosition());
                        break;
                    case Landmark.NOSE_BASE:
                        drawPoint(canvas, landmark.getPosition());
                        break;
                    case Landmark.LEFT_CHEEK:
                        drawPoint(canvas, landmark.getPosition());
                        break;
                    case Landmark.RIGHT_CHEEK:
                        drawPoint(canvas, landmark.getPosition());
                        break;
                    case Landmark.LEFT_EAR:
                        drawPoint(canvas, landmark.getPosition());
                        break;
                    case Landmark.LEFT_EAR_TIP:
                        drawPoint(canvas, landmark.getPosition());
                        break;
                    case Landmark.RIGHT_EAR:
                        drawPoint(canvas, landmark.getPosition());
                        break;
                    case Landmark.RIGHT_EAR_TIP:
                        drawPoint(canvas, landmark.getPosition());
                        break;
                }
            }
            
            /**
             * Other useful details that may be of your interest
             */
            Log.d("", "FaceDetection- FaceId:"+face.getId()
                    + " Smiling:"+face.getIsSmilingProbability()
                    + " LeftEyeOpen:" + face.getIsLeftEyeOpenProbability()
                    + " RightEyeOpen:" + face.getIsRightEyeOpenProbability());
        }

        return outBitmap;
    }

    /**
     * This method draws a rectangle
     */
    private void drawRectangle(Canvas canvas, PointF point, float width, float height){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        float x1 = point.x;
        float y1 = point.y;
        float x2 = x1 + width;
        float y2 = y1 + height;

        RectF rect = new RectF(x1, y1, x2, y2);
        canvas.drawRect(rect, paint);
    }


    /**
     * This method draws a point with hole
     */
    private void drawPoint(Canvas canvas, PointF point){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);

        float x = point.x;
        float y = point.y;

        canvas.drawCircle(x, y, 1, paint);
    }


}

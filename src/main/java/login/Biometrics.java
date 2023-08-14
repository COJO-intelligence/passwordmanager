package login;

import launcher.MainUI;
import org.json.JSONObject;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import storage.APIRequests;
import storage.CryptographicOperations;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Biometrics {

    private Mat loadImage() {
        return Imgcodecs.imread("src/main/resources/lena.png");
    }

    private void saveImage(Mat imageMatrix) {
        Imgcodecs.imwrite("src/main/resources/testResult.png", imageMatrix);
    }

    public String getFeatures() throws InvalidKeySpecException, NoSuchAlgorithmException {
        double[] distance = new double[5];
        nu.pattern.OpenCV.loadShared();

        Mat loadedImage = loadImage();

        Imgproc.cvtColor(loadedImage, loadedImage, Imgproc.COLOR_RGB2GRAY);
        MatOfRect facesDetected = new MatOfRect();
        CascadeClassifier cascadeClassifierFace = new CascadeClassifier();
        int minFaceSize = Math.round(loadedImage.rows() * 0.1f);
        cascadeClassifierFace.load("src/main/resources/haarcascade_frontalface_alt.xml");
        cascadeClassifierFace.detectMultiScale(loadedImage,
                facesDetected,
                1.1,
                3,
                Objdetect.CASCADE_SCALE_IMAGE,
                new Size(minFaceSize, minFaceSize),
                new Size()
        );
        Rect[] facesArray = facesDetected.toArray();
        for(Rect face : facesArray) {
            Imgproc.rectangle(loadedImage, face.tl(), face.br(), new Scalar(0, 0, 255), 3);
            Rect rectCrop = new Rect(face.x, face.y, face.width, face.height);
            Mat imageROI = loadedImage.submat(rectCrop);
            Imgcodecs.imwrite("src/main/resources/face.png", imageROI);

            MatOfRect eyesDetected = new MatOfRect();
            CascadeClassifier cascadeClassifierEye = new CascadeClassifier();
            int minEyeSize = Math.round(imageROI.rows() * 0.2f);
            cascadeClassifierEye.load("src/main/resources/haarcascade_eye.xml");
            //imageROI = loadImage("src/main/resources/face.png");
            cascadeClassifierEye.detectMultiScale(imageROI,
                    eyesDetected,
                    1.1,
                    2,
                    0,
                    new Size(minEyeSize, minEyeSize),
                    new Size());
            Rect[] eyesArray = eyesDetected.toArray();
            for(Rect eye : eyesArray) {
                Imgproc.rectangle(imageROI, eye.tl(), eye.br(), new Scalar(0, 0, 255), 3);
            }
            saveImage(imageROI);
            distance[0] = sqrt(pow(eyesArray[1].x - eyesArray[0].x, 2) + pow(eyesArray[1].y - eyesArray[0].y, 2));
            System.out.println("Distance between eyes: " + distance[0]);

            MatOfRect mouthsDetected = new MatOfRect();
            CascadeClassifier cascadeClassifierMouth = new CascadeClassifier();
            int minMouthSize = Math.round(imageROI.rows() * 0.2f);
            cascadeClassifierMouth.load("src/main/resources/haarcascade_mcs_mouth.xml");
            cascadeClassifierMouth.detectMultiScale(imageROI,
                    mouthsDetected,
                    1.4,
                    2,
                    0,
                    new Size(minMouthSize, minMouthSize),
                    new Size());
            Rect[] mouthArray = mouthsDetected.toArray();
            for(Rect mouth : mouthArray) {
                Imgproc.rectangle(imageROI, mouth.tl(), mouth.br(), new Scalar(0, 0, 255), 3);
                Imgproc.rectangle(imageROI, mouth.tl(), mouth.br(), new Scalar(0, 0, 255), 3);
                Rect rectCropNose = new Rect(mouth.x, mouth.y, mouth.width, mouth.height);
                Mat imageROINose = loadedImage.submat(rectCropNose);
                Mat binary = new Mat(imageROINose.rows(), imageROINose.cols(), imageROINose.type(), new Scalar(0));
                Imgproc.threshold(imageROINose, binary, 100, 255, Imgproc.THRESH_BINARY_INV);
                List<MatOfPoint> contours = new ArrayList<>();
                Mat hierarchy = new Mat();
                Imgproc.findContours(binary, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
                for (MatOfPoint contour : contours) {
                    double cont_area = Imgproc.contourArea(contour);
                    if (cont_area > 1500) {
                        distance[1] = cont_area;
                        //Imgproc.drawContours(draw, contours, i, color, 2, Imgproc.LINE_8, hierarchy, 2, new Point());
                        break;
                    }
                }
            }
            //distance[1] = (double) (mouthArray[0].height * mouthArray[0].width) / 2;
            saveImage(imageROI);
            System.out.println("Mouth area: " + distance[1]);

            MatOfRect nosesDetected = new MatOfRect();
            CascadeClassifier cascadeClassifierNose = new CascadeClassifier();
            int minNoseSize = Math.round(imageROI.rows() * 0.2f);
            cascadeClassifierNose.load("src/main/resources/haarcascade_mcs_nose.xml");
            cascadeClassifierNose.detectMultiScale(imageROI,
                    nosesDetected,
                    1.4,
                    2,
                    0,
                    new Size(minNoseSize, minNoseSize),
                    new Size());
            Rect[] nosesArray = nosesDetected.toArray();
            for(Rect nose : nosesArray) {
                Imgproc.rectangle(imageROI, nose.tl(), nose.br(), new Scalar(0, 0, 255), 3);
                Rect rectCropNose = new Rect(nose.x, nose.y, nose.width, nose.height);
                Mat imageROINose = loadedImage.submat(rectCropNose);
                Mat binary = new Mat(imageROINose.rows(), imageROINose.cols(), imageROINose.type(), new Scalar(0));
                Imgproc.threshold(imageROINose, binary, 100, 255, Imgproc.THRESH_BINARY_INV);
                List<MatOfPoint> contours = new ArrayList<>();
                Mat hierarchy = new Mat();
                Imgproc.findContours(binary, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
                for (MatOfPoint contour : contours) {
                    double cont_area = Imgproc.contourArea(contour);
                    if (cont_area > 1500) {
                        distance[2] = cont_area;
                        //Imgproc.drawContours(draw, contours, i, color, 2, Imgproc.LINE_8, hierarchy, 2, new Point());
                        break;
                    }
                }
                //saveImage(draw, "src/main/resources/NoseCountur.png");
            }
            saveImage(imageROI);
            System.out.println("Nose ridge area: " + distance[2]);

            //distance[2] = (double) (nosesArray[0].height * nosesArray[0].width) / 2;
            //Distance between one eye and nose ridge
            distance[3] = sqrt(pow(nosesArray[0].x - eyesArray[0].x, 2) + pow(nosesArray[0].y - eyesArray[0].y, 2));
            System.out.println("One eye distance: " + distance[3]);
            //Distance between one eye and nose ridge
            distance[4] = sqrt(pow(nosesArray[0].x - eyesArray[1].x, 2) + pow(nosesArray[0].y - eyesArray[1].y, 2));
            System.out.println("Second eye distance: " + distance[4]);




        }
        saveImage(loadedImage);


        StringBuilder doubleString = new StringBuilder();
        for (double v : distance) {
            doubleString.append(setPrecision(v));
        }
        doubleString = new StringBuilder(doubleString.toString().replace(".", ""));
        doubleString = new StringBuilder(new String(CryptographicOperations.getHash(doubleString.toString().toCharArray())));
        return doubleString.toString();
    }

    private String setPrecision(double amt){
        while(amt > 100) {
            amt = amt / 10;
        }
        return String.format("%." + 2 + "f", amt);
    }

    public int sendContent(String features) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, CertificateException, IOException, BadPaddingException, KeyStoreException, InvalidKeyException, KeyManagementException {
        byte[] resultC = CryptographicOperations.encryptContent(MainUI.user.getPassword().getBytes(),
                CryptographicOperations.getAESKeyFromPassword(features.toCharArray()));
        resultC = Base64.getEncoder().encode(resultC);
        return(APIRequests.setUserBioContent(new String(resultC)));
    }

    public String getContent(String bioFeatures) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, CertificateException, IOException, KeyStoreException, KeyManagementException {
        String bioResponse = APIRequests.getUserBioContent();
        JSONObject jsonObject = new JSONObject(bioResponse);
        byte[] decryptedContent = CryptographicOperations.decryptContent(Base64.getDecoder().decode(jsonObject.getString("bioKey")),
                CryptographicOperations.getAESKeyFromPassword(bioFeatures.toCharArray()));
        return(new String(decryptedContent));
    }

}
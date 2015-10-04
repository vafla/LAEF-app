package framework;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.InputStream;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

// jxl Imports

/**
 * Created by Ásta Lovísa on 27.9.2015.
 */


public class ExcelLoader {

    //classmembers.
    String m_filename;
    String TAG = "Workbook";
    //constructor
    public ExcelLoader(String filename) {
        this.m_filename = filename;
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    public void loadFile(Context context, List<ParticipantInfo> participantInfoList) {

        Log.d(TAG, "Loading file: " + m_filename);
        try {
            InputStream file = context.getAssets().open(m_filename);
            Log.d(TAG, m_filename + " has been loaded");
            // if (file.available()!=0) {
                Workbook workbook = Workbook.getWorkbook(file);
            Log.d(TAG, "Creating workbook");
                Sheet sheet = workbook.getSheet(0);
                int namePosition = 0, organisationPosition = 0, countryPosition = 0;
                for (int col = 0; col < sheet.getColumns(); col++) {
                    Cell cell = sheet.getCell(col, 0);
                    if (cell.getContents().equalsIgnoreCase("name")) {
                        namePosition = col;
                    } else if (cell.getContents().equalsIgnoreCase("organisation")) {
                        organisationPosition = col;
                    } else if (cell.getContents().equalsIgnoreCase("country")) {
                        countryPosition = col;
                    }
                }
                for (int row = 1; row < sheet.getRows(); row++) {
                    Cell nameCell = sheet.getCell(namePosition, row);
                    Cell organisationCell = sheet.getCell(organisationPosition, row);
                    Cell countryCell = sheet.getCell(countryPosition, row);

                    ParticipantInfo tempParticipant = new ParticipantInfo(nameCell.getContents(),
                            organisationCell.getContents(),
                            countryCell.getContents());

                    participantInfoList.add(tempParticipant);


                }
            //}
        } catch (BiffException e) {
            Log.d(TAG, e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            e.printStackTrace();
        }
    }

}



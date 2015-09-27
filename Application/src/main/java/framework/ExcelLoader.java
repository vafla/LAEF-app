package framework;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import java.io.File;
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

    //classmemberss.
    String m_filename;

    //constructor
    public ExcelLoader(String filename) {
        this.m_filename = filename;
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    public void loadFile(Context context, List<ParticipantInfo> participantInfoList) {
        try {
            File file = new File(context.getExternalFilesDir(null), m_filename);
            if (file.exists()) {
                Workbook workbook = Workbook.getWorkbook(file);

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
            }
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



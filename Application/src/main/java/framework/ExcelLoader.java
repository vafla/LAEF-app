package framework;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.io.FileInputStream;
import java.io.InputStream;

import al.laefapp.database.DBBitmapUtility;
import al.laefapp.database.ParticipantContract.Countries;
import al.laefapp.database.ParticipantContract.Names;
import al.laefapp.database.ParticipantContract.Organisation;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
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
    public void loadFile(Context context) {

        Log.d(TAG, "Loading file: " + m_filename);
        try {
            InputStream file = new FileInputStream(m_filename);
            WorkbookSettings ws = new WorkbookSettings();
            ws.setEncoding("Cp1252");
            Workbook workbook = Workbook.getWorkbook(file, ws);
            Log.d(TAG, "Creating workbook");
            Sheet sheet = workbook.getSheet(0);
            int namePosition = 0, organisationPosition = 0, countryPosition = 0, infoPosition = 0, imagePos = 0;
            for (int col = 0; col < sheet.getColumns(); col++) {
                Cell cell = sheet.getCell(col, 0);
                if (cell.getContents().equalsIgnoreCase("name")) {
                    namePosition = col;
                } else if (cell.getContents().equalsIgnoreCase("organisation")) {
                    organisationPosition = col;
                } else if (cell.getContents().equalsIgnoreCase("country")) {
                    countryPosition = col;
                } else if (cell.getContents().equalsIgnoreCase("info")) {
                    infoPosition = col;
                } else if (cell.getContents().equalsIgnoreCase("picture")) {
                    imagePos = col;
                }
            }

            ContentValues countryValues = new ContentValues();
            ContentValues orgValues = new ContentValues();
            ContentValues nameValues = new ContentValues();


            for (int row = 1; row < sheet.getRows(); row++) {

                Cell countryCell = sheet.getCell(countryPosition, row);
                // Load the countries
                countryValues.clear();
                countryValues.put(Countries.COUNTRY, countryCell.getContents());

                Uri countryUri = context.getContentResolver().insert(Countries.CONTENT_URI,
                        countryValues);
                long countryID = ContentUris.parseId(countryUri);
                if (countryID == -1) {
                    Log.d(TAG, "Country has already been added to database ");
                    String whereClause = Countries.COUNTRY + "=?";
                    String[] whereArgs = new String[]{countryCell.getContents()};
                    Cursor cursor = context.getContentResolver().query(Countries.CONTENT_URI,
                            Countries.PROJECTION_ALL, whereClause, whereArgs, null);

                    if (cursor != null) {
                        cursor.moveToFirst();

                        countryID = cursor.getLong(cursor.getColumnIndex(Countries._ID));

                        Log.d(TAG, "Only one cursor? " + cursor.isAfterLast());

                    }

                    cursor.close();

                }
                Log.d(TAG, "Country ID " + countryID);

                //Load organisation
                Cell organisationCell = sheet.getCell(organisationPosition, row);
                orgValues.clear();
                orgValues.put(Organisation.ORGANISATION, organisationCell.getContents());
                orgValues.put(Organisation.COUNTRY_ID, countryID);

                Uri orgUri = context.getContentResolver().insert(Organisation.CONTENT_URI,
                        orgValues);
                long orgID = ContentUris.parseId(orgUri);
                if (orgID == -1) {
                    Log.d(TAG, "Organisation has already been added to database ");
                    String whereClause = Organisation.ORGANISATION + "=?";
                    String[] whereArgs = new String[]{organisationCell.getContents()};
                    Cursor cursor = context.getContentResolver().query(Organisation.CONTENT_URI,
                            Organisation.PROJECTION_ALL, whereClause, whereArgs, null);

                    if (cursor != null) {
                        cursor.moveToFirst();

                        orgID = cursor.getLong(cursor.getColumnIndex(Organisation._ID));

                        Log.d(TAG, "Only one cursor? " + cursor.isAfterLast());
                    }

                    cursor.close();

                }
                Log.d(TAG, "Organisation ID " + orgID);


                // Load Name
                Cell nameCell = sheet.getCell(namePosition, row);
                Cell infoCell = sheet.getCell(infoPosition, row);
                Cell imageURLCell = sheet.getCell(imagePos, row);
                nameValues.clear();
                nameValues.put(Names.NAME, nameCell.getContents());
                nameValues.put(Names.DESCRIPTION, infoCell.getContents());
                nameValues.put(Names.ORGANISATION_ID, orgID);

                Bitmap image = new DownloadImageTask().execute(imageURLCell.getContents()).get();
                if (image != null) {
                    Log.d(TAG, nameCell.getContents() + " has an image");
                    nameValues.put(Names.IMAGE, DBBitmapUtility.getBytes(image));
                } else {
                    Log.d(TAG, nameCell.getContents() + " has no image");
                }

                Uri nameUri = context.getContentResolver().insert(Names.CONTENT_URI, nameValues);
                long nameID = ContentUris.parseId(nameUri);
                Log.d(TAG, "Name ID " + nameID);

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



package al.laefapp.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by lovisa on 10/27/15.
 * The Database Columns
 */
public final class ParticipantContract {

    /*
     * The authority of the participant provider
     */
    public static final String AUTHORITY = "database.participants";

    /*
     * The content URI for the top-level
     * participant authority.
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    /**
     * A selection clause for ID based queries.
     */
    public static final String SELECTION_ID_BASED = BaseColumns._ID + " = ? ";

    /**
     * Constants for the Countries table
     */
    public static final class Countries implements CommonColumns {

        /**
         * The content URI for this table.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ParticipantContract.CONTENT_URI, "countries");
        /**
         * The mime type of a directory of items.
         */
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.app.participants_countries";
        /**
         * The mime type of a single item.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.app.participants_countries";
        /**
         * A projection of all columns in the items table.
         */
        public static final String[] PROJECTION_ALL = {_ID, COUNTRY};
        /**
         * The default sort order for queries containing NAME fields.
         */
        public static final String SORT_ORDER_DEFAULT = COUNTRY + " ASC";

    }

    /**
     * Constants for the Organisation table
     */

    public static final class Organisation implements CommonColumns {

        /**
         * The content URI for this table.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ParticipantContract.CONTENT_URI, "organisation");
        /**
         * The mime type of a directory of items.
         */
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.app.participants_organisation";
        /**
         * The mime type of a single item.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.app.participants_organisation";

        /**
         * ID of the country the organisation belongs to
         */
        public static final String COUNTRY_ID = "country_id";
        /**
         * A projection of all columns in the items table.
         */
        public static final String[] PROJECTION_ALL = {_ID, ORGANISATION, COUNTRY_ID};
        /**
         * The default sort order for queries containing NAME fields.
         */
        public static final String SORT_ORDER_DEFAULT = ORGANISATION + " ASC";

    }

    /**
     * Constants for the Participant Names table
     */
    public static final class Names implements CommonColumns {

        /**
         * The content URI for this table.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ParticipantContract.CONTENT_URI, "names");
        /**
         * The mime type of a directory of items.
         */
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.app.participants_names";
        /**
         * The mime type of a single item.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.app.participants_names";

        public static final String IMAGE = "image";
        /**
         * ID of the organisation the partcipant belongs to
         */
        public static final String ORGANISATION_ID = "organisation_id";
        /**
         * A projection of all columns in the items table.
         */
        public static final String[] PROJECTION_ALL = {_ID, NAME, DESCRIPTION, IMAGE, ORGANISATION_ID};
        /**
         * The default sort order for queries containing NAME fields.
         */
        public static final String SORT_ORDER_DEFAULT = NAME + " ASC";
    }


    /**
     * This interface defines common columns found in multiple tables.
     */
    public interface CommonColumns extends BaseColumns {
        /**
         * The name of the item.
         */
        String COUNTRY = "country";

        String ORGANISATION = "organisation";

        String NAME = "name";

        String DESCRIPTION = "description";

    }
}

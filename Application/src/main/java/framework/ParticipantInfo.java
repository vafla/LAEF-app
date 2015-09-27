package framework;

/**
 * Created by Ásta Lovísa on 27.9.2015.
 */
public class ParticipantInfo {

    //classmember
    String m_name;
    String m_organisation;
    String m_country;

    //// TODO: 27.9.2015 Add get info and picture 

    public ParticipantInfo(String name, String organisation, String country) {
        this.m_name = name;
        this.m_organisation = organisation;
        this.m_country = country;
    }

    public String getCountry() {
        return m_country;
    }

    public String getOrganisation() {
        return m_organisation;
    }

    public String getName() {
        return m_name;
    }
}

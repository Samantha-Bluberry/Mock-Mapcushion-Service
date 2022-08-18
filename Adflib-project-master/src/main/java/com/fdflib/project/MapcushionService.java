package com.fdflib.project;

import com.fdflib.project.model.*;
import com.fdflib.project.service.*;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.persistence.database.DatabaseUtil;
import com.fdflib.service.FdfServices;
import com.fdflib.util.FdfSettings;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MapcushionService {
    public static void main(String[] args) {
        System.out.println("Hello 4DF World!"); // Display the string.

        // use the  settings within this method to customize the 4DFLib.  Note, everything in this method is optional.
        setOptionalSettings();

        // Create a array that will hold the classes that make up our 4df data model
        List<Class> myModel = new ArrayList<>();

        // Add our classes
        myModel.add(Client.class);
        myModel.add(Address.class);
        myModel.add(UserIdentity.class);
        myModel.add(Users.class);
        myModel.add(Location.class);
        myModel.add(Guest.class);
        myModel.add(SystemRole.class);
        myModel.add(ClientRole.class);
        myModel.add(SystemRoleUserAccess.class);
        myModel.add(ClientRoleUserAccess.class);
        myModel.add(Device.class);
        myModel.add(Floor.class);
        myModel.add(Beacon.class);
        myModel.add(Ping.class);
        myModel.add(PingBeacon.class);

        // call the initialization of library!
        FdfServices.initializeFdfDataModel(myModel);

        // insert some demo data
        try {
            insertData();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        // do a few queries and output the results

    }

    /**
     * Everything set in this method is optional, but useful
     */
    private static void setOptionalSettings() {

        // get the 4dflib settings singleton
        FdfSettings fdfSettings = FdfSettings.getInstance();

        // set the database type and name and connection information
        // PostgreSQL settings
        //fdfSettings.PERSISTENCE = DatabaseUtil.DatabaseType.POSTGRES;
        //fdfSettings.DB_PROTOCOL = DatabaseUtil.DatabaseProtocol.JDBC_POSTGRES;

        // postgres default root user
        // root user settings are only required for initial database creation.  Once the database is created you
        // should remove this information
        //fdfSettings.DB_ROOT_USER = "postgres";

        // MySQL settings
        fdfSettings.PERSISTENCE = DatabaseUtil.DatabaseType.MYSQL;
        fdfSettings.DB_PROTOCOL = DatabaseUtil.DatabaseProtocol.JDBC_MYSQL;

        // MariaDB and MySQL default
        // root user settings are only required for initial database creation.  Once the database is created you
        // should remove this information
        fdfSettings.DB_ROOT_USER = "root";

        // root user password
        fdfSettings.DB_ROOT_PASSWORD = "";

        // Database encoding
        fdfSettings.DB_ENCODING = DatabaseUtil.DatabaseEncoding.UTF8;

        // Application Database name
        fdfSettings.DB_NAME = "Mapcushion";

        // Database host
        fdfSettings.DB_HOST = "localhost";

        // Port is not required for DB defaults can be changed when needed
        fdfSettings.DB_PORT = 3306;

        // Database user information
        fdfSettings.DB_USER = "mapcush";
        fdfSettings.DB_PASSWORD = "mapcushionpass";






        // set the default system information
        fdfSettings.DEFAULT_SYSTEM_NAME = "Mapcushion Core API";
        fdfSettings.DEFAULT_SYSTEM_DESCRIPTION = "Central API service for the Mapcushion Application";

        // set the default tenant information
        fdfSettings.DEFAULT_TENANT_NAME = "Mapcushion";
        fdfSettings.DEFAULT_TENANT_DESRIPTION = "Main system Tenant";
        fdfSettings.DEFAULT_TENANT_IS_PRIMARY = true;
        fdfSettings.DEFAULT_TENANT_WEBSITE = "http://www.4dflib.com";

        // local dev, no ssl
        fdfSettings.USE_SSL = false;

    }

    private static void insertData() throws InterruptedException {

        ClientService cs = new ClientService();
        SystemRoleService srs = new SystemRoleService();
        ClientRoleService crs = new ClientRoleService();
        UserIdentityService uis = new UserIdentityService();
        UsersService us = new UsersService();
        AddressService as = new AddressService();
        ClientRoleUserAccessService cruas = new ClientRoleUserAccessService();
        SystemRoleUserAccessService sruas = new SystemRoleUserAccessService();
        LocationService ls = new LocationService();
        FloorService fs = new FloorService();
        BeaconService bs = new BeaconService();
        GuestService gs = new GuestService();

        //insert default system roles
        SystemRole omniAdministrator = new SystemRole();
        omniAdministrator.roleName = "Omni-Administrator";
        omniAdministrator.roleDescription = "Can administer any client, can also create, edit or delete clients";
        omniAdministrator = srs.saveSystemRole(omniAdministrator);

        SystemRole clientAdministrator = new SystemRole();
        clientAdministrator.roleName = "Client-Administrator";
        clientAdministrator.roleDescription = "Can administer information within client account";
        clientAdministrator = srs.saveSystemRole(clientAdministrator);

        SystemRole clientManager = new SystemRole();
        clientManager.roleName = "Client-Manager";
        clientManager.roleDescription = "Manager of client account can perform client operations including managing maps, beacons and viewing users current and past locations";
        clientManager = srs.saveSystemRole(clientManager);

        SystemRole clientBeaconManager = new SystemRole();
        clientBeaconManager.roleName = "Client-Beacon-Manager";
        clientBeaconManager.roleDescription = "Manager of client account can perform client operations including managing beacons and viewing users current locations";
        clientBeaconManager = srs.saveSystemRole(clientBeaconManager);

        SystemRole clientMapViewer = new SystemRole();
        clientMapViewer.roleName = "Client-Map-Viewer";
        clientMapViewer.roleDescription = "Client account with privileges to view users current location information";
        clientMapViewer = srs.saveSystemRole(clientMapViewer);

        SystemRole clientUser = new SystemRole();
        clientUser.roleName = "Client-User";
        clientUser.roleDescription = "Basic client user account can report locations to the system but not see maps or locations";
        clientUser = srs.saveSystemRole(clientUser);

        Thread.sleep(3000);

        //insert clients
        Client maristSecurity = new Client();
        maristSecurity.clientOrgName = "Marist Security";
        maristSecurity = cs.saveClient(maristSecurity);

        Client hudsonBank = new Client();
        hudsonBank.clientOrgName = "Hudson Bank";
        hudsonBank = cs.saveClient(hudsonBank);

        //insert custom roles
        ClientRole accountAdder = new ClientRole();
        accountAdder.roleName = "Account-Adder";
        accountAdder.roleDescription = "Role for for front-desk staff to manage and create user accounts and guest";
        accountAdder.uuid = maristSecurity.id;
        accountAdder = crs.saveClientRole(accountAdder);

        ClientRole maintenanceStaff = new ClientRole();
        maintenanceStaff.roleName = "Maintenance-Staff";
        maintenanceStaff.roleDescription = "Role for maintenance staff to monitor beacons and devices but not see maps";
        maintenanceStaff.uuid = maristSecurity.id;
        maintenanceStaff = crs.saveClientRole(maintenanceStaff);

        ClientRole analyticsRole = new ClientRole();
        analyticsRole.roleName = "Analytics-Role";
        analyticsRole.roleDescription = "Role for professors and grad students to gather data for research and analytics";
        analyticsRole.uuid = maristSecurity.id;
        analyticsRole = crs.saveClientRole(analyticsRole);


        //insert users
        UserIdentity amMoCard = new UserIdentity();
        amMoCard.userIdType = "Driver's License";
        amMoCard.userIdNum1 = "8e23689se";
        amMoCard.userIdNum2 = "844ber348";
        amMoCard.userIdDateIssue = new Date(2018, 7, 3);
        amMoCard.userIdDateExpire = new Date(2021, 9, 1);
        amMoCard = uis.saveUserIdentity(amMoCard);

        Address amMoAddress = new Address();
        amMoAddress.addressLine1 = "36 Hunter St";
        amMoAddress.addressLine2 = "Appt 57";
        amMoAddress.addressCity = "Poughkeepsie";
        amMoAddress.addressState = "New York";
        amMoAddress.addressCountry = "United States";
        amMoAddress.addressZIP = "13590";
        amMoAddress = as.saveAddress(amMoAddress);

        Users amMo  = new Users();
        amMo.userFirstName = "Amara";
        amMo.userLastName = "Montgomery";
        amMo.userEmail = "Ama4783@gmail.com";
        amMo.userPassword = "u67j257shallow";
        amMo.userColor = "Green";
        amMo.userDOB = new Date(1985,2,15);
        amMo.userGender = 'F';
        amMo.userHeight = 67;
        amMo.userWeight = 135;
        amMo.userEyeColor = "Brown";
        amMo.userIdentityID = amMoCard.id;
        amMo.addressID = amMoAddress.id;
        amMo = us.saveUsers(amMo);

        UserIdentity erFuCard = new UserIdentity();
        erFuCard.userIdType = "Passport";
        erFuCard.userIdNum1 = "G7te5e3756";
        erFuCard.userIdNum2 = "r57uSui7uy";
        erFuCard.userIdDateIssue = new Date(2019, 1, 16);
        erFuCard.userIdDateExpire = new Date(2022, 5, 1);
        erFuCard = uis.saveUserIdentity(erFuCard);

        Address erFuAddress = new Address();
        erFuAddress.addressLine1 = "54 Grove St";
        erFuAddress.addressCity = "Poughkeepsie";
        erFuAddress.addressState = "New York";
        erFuAddress.addressCountry = "United States";
        erFuAddress.addressZIP = "13590";
        erFuAddress = as.saveAddress(erFuAddress);

        Users erFu  = new Users();
        erFu.userFirstName = "Erik";
        erFu.userLastName = "Fuentes";
        erFu.userEmail = "ErikFumes@gmail.com";
        erFu.userPassword = "password123";
        erFu.userColor = "Red";
        erFu.userDOB = new Date(1980,5,9);
        erFu.userGender = 'M';
        erFu.userEyeColor = "Brown";
        erFu.userIdentityID = erFuCard.id;
        erFu.addressID = erFuAddress.id;
        erFu = us.saveUsers(erFu);

        UserIdentity caPaCard = new UserIdentity();
        caPaCard.userIdType = "Driver's License";
        caPaCard.userIdNum1 = " br765efv65";
        caPaCard.userIdNum2 = "579cv36uf";
        caPaCard.userIdDateIssue = new Date(2018, 3, 6);
        caPaCard.userIdDateExpire = new Date(2021, 5, 1);
        caPaCard = uis.saveUserIdentity(caPaCard);

        Address caPaAddress = new Address();
        caPaAddress.addressLine1 = "32 Follow Dr";
        caPaAddress.addressLine2 = "Appt 64";
        caPaAddress.addressCity = "Wappingers Falls";
        caPaAddress.addressState = "New York";
        caPaAddress.addressCountry = "United States";
        caPaAddress.addressZIP = "13590";
        caPaAddress = as.saveAddress(caPaAddress);

        Users caPa  = new Users();
        caPa.userFirstName = "Carolyn";
        caPa.userLastName = "Parks";
        caPa.userEmail = "Park72@yahoo.com";
        caPa.userPassword = "39visit2355e";
        caPa.userColor = "Blue";
        caPa.userDOB = new Date(1972,4,24);
        caPa.userGender = 'F';
        caPa.userHeight = 63;
        caPa.userWeight = 145;
        caPa.userIdentityID = caPaCard.id;
        caPa.addressID = caPaAddress.id;
        caPa = us.saveUsers(caPa);

        UserIdentity gaRoCard = new UserIdentity();
        gaRoCard.userIdType = "Driver's License";
        gaRoCard.userIdNum1 = "h65u3ee45u6";
        gaRoCard.userIdNum2 = "6ns436uf";
        gaRoCard.userIdDateIssue = new Date(2017, 3, 12);
        gaRoCard.userIdDateExpire = new Date(2020, 1, 1);
        gaRoCard = uis.saveUserIdentity(gaRoCard);

        Address gaRoAddress = new Address();
        gaRoAddress.addressLine1 = "41 Haven Dr";
        gaRoAddress.addressCity = "Poughkeepsie";
        gaRoAddress.addressState = "New York";
        gaRoAddress.addressCountry = "United States";
        gaRoAddress.addressZIP = "13590";
        gaRoAddress = as.saveAddress(gaRoAddress);

        Users gaRo  = new Users();
        gaRo.userFirstName = "Gary";
        gaRo.userLastName = "Roswell";
        gaRo.userEmail = "GaryRoss3@gmail.com";
        gaRo.userPassword = "zdet56yhtre7jwa3";
        gaRo.userColor = "Blue";
        gaRo.userDOB = new Date(1993,10,29);
        gaRo.userGender = 'M';
        gaRo.userHeight = 67;
        gaRo.userWeight = 161;
        gaRo.userEyeColor = "Brown";
        gaRo.userIdentityID = gaRoCard.id;
        gaRo.addressID = gaRoAddress.id;
        gaRo = us.saveUsers(gaRo);

        UserIdentity roBiCard = new UserIdentity();
        roBiCard.userIdType = "Driver's License";
        roBiCard.userIdNum1 = "8e23689se";
        roBiCard.userIdNum2 = "844ber348";
        roBiCard.userIdDateIssue = new Date(2018, 4, 8);
        roBiCard.userIdDateExpire = new Date(2021, 11, 1);
        roBiCard = uis.saveUserIdentity(roBiCard);

        Address roBiAddress = new Address();
        roBiAddress.addressLine1 = "15 Pike St";
        roBiAddress.addressCity = "Poughkeepsie";
        roBiAddress.addressState = "New York";
        roBiAddress.addressCountry = "United States";
        roBiAddress.addressZIP = "13590";
        roBiAddress = as.saveAddress(roBiAddress);

        Users roBi  = new Users();
        roBi.userFirstName = "Robert";
        roBi.userLastName = "Binkley";
        roBi.userEmail = "Roboto97.com";
        roBi.userPassword = "roboto";
        roBi.userColor = "Black";
        roBi.userDOB = new Date(1997,6,1);
        roBi.userGender = 'M';
        roBi.userHeight = 74;
        roBi.userWeight = 170;
        roBi.userEyeColor = "Green";
        roBi.userIdentityID = roBiCard.id;
        roBi.addressID = roBiAddress.id;
        roBi = us.saveUsers(roBi);

        UserIdentity vaRiCard = new UserIdentity();
        vaRiCard.userIdType = "Driver's License";
        vaRiCard.userIdNum1 = "55yursdf4";
        vaRiCard.userIdNum2 = "eehhndfz";
        vaRiCard.userIdDateIssue = new Date(2018, 7, 3);
        vaRiCard.userIdDateExpire = new Date(2021, 9, 1);
        vaRiCard = uis.saveUserIdentity(vaRiCard);

        Address vaRiAddress = new Address();
        vaRiAddress.addressLine1 = "11 Adams Dr";
        vaRiAddress.addressLine2 = "Appt 23";
        vaRiAddress.addressCity = "Newburgh";
        vaRiAddress.addressState = "New York";
        vaRiAddress.addressCountry = "United States";
        vaRiAddress.addressZIP = "12835";
        vaRiAddress = as.saveAddress(vaRiAddress);

        Users vaRi  = new Users();
        vaRi.userFirstName = "Vanessa";
        vaRi.userLastName = "Richards";
        vaRi.userEmail = "VanessaR352@gmail.com";
        vaRi.userPassword = "green34565";
        vaRi.userColor = "Yellow";
        vaRi.userDOB = new Date(1979,11,10);
        vaRi.userGender = 'F';
        vaRi.userEyeColor = "Blue";
        vaRi.userIdentityID = vaRiCard.id;
        vaRi.addressID = vaRiAddress.id;
        vaRi = us.saveUsers(vaRi);

        UserIdentity evRiCard = new UserIdentity();
        evRiCard.userIdType = "Passport";
        evRiCard.userIdNum1 = "54her5u46";
        evRiCard.userIdNum2 = "54tgefb54";
        evRiCard.userIdDateIssue = new Date(2019, 1, 16);
        evRiCard.userIdDateExpire = new Date(2022, 5, 1);
        evRiCard = uis.saveUserIdentity(evRiCard);

        Address evRiAddress = new Address();
        evRiAddress.addressLine1 = "12 Upland Ave";
        evRiAddress.addressCity = "Poughkeepsie";
        evRiAddress.addressState = "New York";
        evRiAddress.addressCountry = "United States";
        evRiAddress.addressZIP = "13590";
        evRiAddress = as.saveAddress(evRiAddress);

        Users evRi  = new Users();
        evRi.userFirstName = "Eva";
        evRi.userLastName = "Rife";
        evRi.userEmail = "Eva4673@gmail.com";
        evRi.userPassword = "aey5dwe3";
        evRi.userColor = "Blue";
        evRi.userDOB = new Date(1976,11,13);
        evRi.userGender = 'F';
        evRi.userEyeColor = "Hazel";
        evRi.userIdentityID = evRiCard.id;
        evRi.addressID = evRiAddress.id;
        evRi = us.saveUsers(evRi);

        UserIdentity jaRaCard = new UserIdentity();
        jaRaCard.userIdType = "Driver's License";
        jaRaCard.userIdNum1 = " breh43b345";
        jaRaCard.userIdNum2 = "ber34tgr3t45";
        jaRaCard.userIdDateIssue = new Date(2018, 3, 6);
        jaRaCard.userIdDateExpire = new Date(2021, 5, 1);
        jaRaCard = uis.saveUserIdentity(jaRaCard);

        Address jaRaAddress = new Address();
        jaRaAddress.addressLine1 = "46 Pursglove Dr";
        jaRaAddress.addressCity = "Wappingers Falls";
        jaRaAddress.addressState = "New York";
        jaRaAddress.addressCountry = "United States";
        jaRaAddress.addressZIP = "13590";
        jaRaAddress = as.saveAddress(jaRaAddress);

        Users jaRa  = new Users();
        jaRa.userFirstName = "James";
        jaRa.userLastName = "Ramos";
        jaRa.userEmail = "Ramos346@yahoo.com";
        jaRa.userPassword = "12345678";
        jaRa.userColor = "Blue";
        jaRa.userDOB = new Date(1985,10,19);
        jaRa.userGender = 'M';
        jaRa.userHeight = 69;
        jaRa.userWeight = 230;
        jaRa.userIdentityID = jaRaCard.id;
        jaRa.addressID = jaRaAddress.id;
        jaRa = us.saveUsers(jaRa);

        UserIdentity alFiCard = new UserIdentity();
        alFiCard.userIdType = "Driver's License";
        alFiCard.userIdNum1 = "ery54hnre";
        alFiCard.userIdNum2 = "e4t4trn54y";
        alFiCard.userIdDateIssue = new Date(2017, 3, 12);
        alFiCard.userIdDateExpire = new Date(2020, 1, 1);
        alFiCard = uis.saveUserIdentity(alFiCard);

        Address alFiAddress = new Address();
        alFiAddress.addressLine1 = "26 Kemberly Dr";
        alFiAddress.addressCity = "Poughkeepsie";
        alFiAddress.addressState = "New York";
        alFiAddress.addressCountry = "United States";
        alFiAddress.addressZIP = "13590";
        alFiAddress = as.saveAddress(alFiAddress);

        Users alFi  = new Users();
        alFi.userFirstName = "Alejandro";
        alFi.userLastName = "Figueroa";
        alFi.userEmail = "Al3267@gmail.com";
        alFi.userPassword = "yelllll00w";
        alFi.userColor = "Black";
        alFi.userDOB = new Date(1985,9,13);
        alFi.userGender = 'M';
        alFi.userHeight = 72;
        alFi.userWeight = 210;
        alFi.userEyeColor = "Brown";
        alFi.userIdentityID = alFiCard.id;
        alFi.addressID = alFiAddress.id;
        alFi = us.saveUsers(alFi);

        UserIdentity caHaCard = new UserIdentity();
        caHaCard.userIdType = "State ID";
        caHaCard.userIdNum1 = "reh4hdfsw";
        caHaCard.userIdNum2 = "w45terdfhe5";
        caHaCard.userIdDateIssue = new Date(2018, 4, 8);
        caHaCard.userIdDateExpire = new Date(2021, 11, 1);
        caHaCard = uis.saveUserIdentity(caHaCard);

        Address caHaAddress = new Address();
        caHaAddress.addressLine1 = "15 Poe Rd";
        caHaAddress.addressCity = "Poughkeepsie";
        caHaAddress.addressState = "New York";
        caHaAddress.addressCountry = "United States";
        caHaAddress.addressZIP = "13590";
        caHaAddress = as.saveAddress(caHaAddress);

        Users caHa  = new Users();
        caHa.userFirstName = "Cathrine";
        caHa.userLastName = "Hagler";
        caHa.userEmail = "CathrineHagler.com";
        caHa.userPassword = "b45eyheb";
        caHa.userColor = "Purple";
        caHa.userDOB = new Date(1996,5,27);
        caHa.userGender = 'F';
        caHa.userHeight = 78;
        caHa.userWeight = 152;
        caHa.userEyeColor = "Green";
        caHa.userIdentityID = caHaCard.id;
        caHa.addressID = caHaAddress.id;
        caHa = us.saveUsers(caHa);

        UserIdentity viJoCard = new UserIdentity();
        viJoCard.userIdType = "Driver's License";
        viJoCard.userIdNum1 = "43gber3";
        viJoCard.userIdNum2 = "eegb4t3431";
        viJoCard.userIdDateIssue = new Date(2018, 7, 3);
        viJoCard.userIdDateExpire = new Date(2021, 9, 1);
        viJoCard = uis.saveUserIdentity(viJoCard);

        Address viJoAddress = new Address();
        viJoAddress.addressLine1 = "22 Melody Lane";
        viJoAddress.addressLine2 = "Appt 12";
        viJoAddress.addressCity = "Poughkeepsie";
        viJoAddress.addressState = "New York";
        viJoAddress.addressCountry = "United States";
        viJoAddress.addressZIP = "13590";
        viJoAddress = as.saveAddress(viJoAddress);

        Users viJo  = new Users();
        viJo.userFirstName = "Victor";
        viJo.userLastName = "Johnson";
        viJo.userEmail = "Victory145@gmail.com";
        viJo.userPassword = "wavews43tygreb";
        viJo.userColor = "Red";
        viJo.userDOB = new Date(1979,6,16);
        viJo.userGender = 'M';
        viJo.userHeight = 71;
        viJo.userWeight = 212;
        viJo.userEyeColor = "Brown";
        viJo.userIdentityID = viJoCard.id;
        viJo.addressID = viJoAddress.id;
        viJo = us.saveUsers(viJo);

        UserIdentity keMoCard = new UserIdentity();
        keMoCard.userIdType = "Passport";
        keMoCard.userIdNum1 = "34gber3";
        keMoCard.userIdNum2 = "34yhnm5ntr4";
        keMoCard.userIdDateIssue = new Date(2019, 1, 16);
        keMoCard.userIdDateExpire = new Date(2022, 5, 1);
        keMoCard = uis.saveUserIdentity(keMoCard);

        Address keMoAddress = new Address();
        keMoAddress.addressLine1 = "66 Abner Rd";
        keMoAddress.addressCity = "Danbury";
        keMoAddress.addressState = "Connecticut";
        keMoAddress.addressCountry = "United States";
        keMoAddress.addressZIP = "14632";
        keMoAddress = as.saveAddress(keMoAddress);

        Users keMo  = new Users();
        keMo.userFirstName = "Kenneth";
        keMo.userLastName = "Mohr";
        keMo.userEmail = "KenMorh452@gmail.com";
        keMo.userPassword = "w4tegvw2rsss";
        keMo.userColor = "Red";
        keMo.userDOB = new Date(1980,1,11);
        keMo.userGender = 'M';
        keMo.userEyeColor = "Brown";
        keMo.userIdentityID = keMoCard.id;
        keMo.addressID = keMoAddress.id;
        keMo = us.saveUsers(keMo);

        UserIdentity liCuCard = new UserIdentity();
        liCuCard.userIdType = "Driver's License";
        liCuCard.userIdNum1 = " 24tgbe34";
        liCuCard.userIdNum2 = "ewgbeerg3";
        liCuCard.userIdDateIssue = new Date(2018, 3, 6);
        liCuCard.userIdDateExpire = new Date(2021, 5, 1);
        liCuCard = uis.saveUserIdentity(liCuCard);

        Address liCuAddress = new Address();
        liCuAddress.addressLine1 = "22 Hampton Dr";
        liCuAddress.addressLine2 = "Appt 26";
        liCuAddress.addressCity = "Wappingers Falls";
        liCuAddress.addressState = "New York";
        liCuAddress.addressCountry = "United States";
        liCuAddress.addressZIP = "13590";
        liCuAddress = as.saveAddress(liCuAddress);

        Users liCu  = new Users();
        liCu.userFirstName = "Lisa";
        liCu.userLastName = "Cuffe";
        liCu.userEmail = "Cuffe2356@yahoo.com";
        liCu.userPassword = "leeaaavvve2020";
        liCu.userColor = "Purple";
        liCu.userDOB = new Date(1993,12,20);
        liCu.userGender = 'F';
        liCu.userHeight = 63;
        liCu.userWeight = 163;
        liCu.userIdentityID = liCuCard.id;
        liCu.addressID = liCuAddress.id;
        liCu = us.saveUsers(liCu);

        UserIdentity kaSeCard = new UserIdentity();
        kaSeCard.userIdType = "Driver's License";
        kaSeCard.userIdNum1 = "3tgbe34g3";
        kaSeCard.userIdNum2 = "e3g3vrb4h4";
        kaSeCard.userIdDateIssue = new Date(2017, 3, 12);
        kaSeCard.userIdDateExpire = new Date(2020, 1, 1);
        kaSeCard = uis.saveUserIdentity(kaSeCard);

        Address kaSeAddress = new Address();
        kaSeAddress.addressLine1 = "37 Coal St";
        kaSeAddress.addressCity = "Poughkeepsie";
        kaSeAddress.addressState = "New York";
        kaSeAddress.addressCountry = "United States";
        kaSeAddress.addressZIP = "13590";
        kaSeAddress = as.saveAddress(kaSeAddress);

        Users kaSe  = new Users();
        kaSe.userFirstName = "Karen";
        kaSe.userLastName = "Servin";
        kaSe.userEmail = "Karen1Servin3@gmail.com";
        kaSe.userPassword = "passw0rd";
        kaSe.userColor = "Blue";
        kaSe.userDOB = new Date(1984,3,9);
        kaSe.userGender = 'F';
        kaSe.userHeight = 66;
        kaSe.userWeight = 133;
        kaSe.userEyeColor = "Brown";
        kaSe.userIdentityID = kaSeCard.id;
        kaSe.addressID = kaSeAddress.id;
        kaSe = us.saveUsers(kaSe);

        UserIdentity raGrCard = new UserIdentity();
        raGrCard.userIdType = "Driver's License";
        raGrCard.userIdNum1 = "43tyhbnrt56";
        raGrCard.userIdNum2 = "82g23g48";
        raGrCard.userIdDateIssue = new Date(2018, 4, 8);
        raGrCard.userIdDateExpire = new Date(2021, 11, 1);
        raGrCard = uis.saveUserIdentity(raGrCard);

        Address raGrAddress = new Address();
        raGrAddress.addressLine1 = "30 Hall St";
        raGrAddress.addressCity = "Poughkeepsie";
        raGrAddress.addressState = "New York";
        raGrAddress.addressCountry = "United States";
        raGrAddress.addressZIP = "13590";
        raGrAddress = as.saveAddress(raGrAddress);

        Users raGr  = new Users();
        raGr.userFirstName = "Raul";
        raGr.userLastName = "Gray";
        raGr.userEmail = "Raul36.com";
        raGr.userPassword = "vfrb3v3g3";
        raGr.userColor = "Blue";
        raGr.userDOB = new Date(1977,1,1);
        raGr.userGender = 'M';
        raGr.userHeight = 66;
        raGr.userWeight = 175;
        raGr.userEyeColor = "Green";
        raGr.userIdentityID = raGrCard.id;
        raGr.addressID = raGrAddress.id;
        raGr = us.saveUsers(raGr);

        UserIdentity deMiCard = new UserIdentity();
        deMiCard.userIdType = "Driver's License";
        deMiCard.userIdNum1 = "egbryn53534g3";
        deMiCard.userIdNum2 = "reh34t4654yh56y4";
        deMiCard.userIdDateIssue = new Date(2018, 7, 3);
        deMiCard.userIdDateExpire = new Date(2021, 9, 1);
        deMiCard = uis.saveUserIdentity(deMiCard);

        Address deMiAddress = new Address();
        deMiAddress.addressLine1 = "43 Arrowood Dr";
        deMiAddress.addressLine2 = "Appt 62";
        deMiAddress.addressCity = "Newburgh";
        deMiAddress.addressState = "New York";
        deMiAddress.addressCountry = "United States";
        deMiAddress.addressZIP = "12835";
        deMiAddress = as.saveAddress(deMiAddress);

        Users deMi  = new Users();
        deMi.userFirstName = "Deborah";
        deMi.userLastName = "Miller";
        deMi.userEmail = "DeborahMiller@gmail.com";
        deMi.userPassword = "grbbnet3gebrbf";
        deMi.userColor = "Yellow";
        deMi.userDOB = new Date(1995,2,23);
        deMi.userGender = 'F';
        deMi.userEyeColor = "Blue";
        deMi.userIdentityID = deMiCard.id;
        deMi.addressID = deMiAddress.id;
        deMi = us.saveUsers(deMi);

        SystemRoleUserAccess s1 = new SystemRoleUserAccess();
        s1.systemRoleID = omniAdministrator.id;
        s1.userID = alFi.id;
        s1.uuid = hudsonBank.id;
        sruas.saveSystemRoleUserAccess(s1);

        SystemRoleUserAccess s2 = new SystemRoleUserAccess();
        s2.systemRoleID = omniAdministrator.id;
        s2.userID = alFi.id;
        s2.uuid = maristSecurity.id;
        sruas.saveSystemRoleUserAccess(s2);

        ClientRoleUserAccess c3 = new ClientRoleUserAccess();
        c3.clientRoleID = analyticsRole.id;
        c3.userID = alFi.id;
        c3.uuid = maristSecurity.id;
        cruas.saveClientRoleUserAccess(c3);

        SystemRoleUserAccess s4 = new SystemRoleUserAccess();
        s4.systemRoleID = clientUser.id;
        s4.userID = caHa.id;
        s4.uuid = hudsonBank.id;
        sruas.saveSystemRoleUserAccess(s4);

        ClientRoleUserAccess c5 = new ClientRoleUserAccess();
        c5.clientRoleID = accountAdder.id;
        c5.userID = caHa.id;
        c5.uuid = maristSecurity.id;
        cruas.saveClientRoleUserAccess(c5);

        SystemRoleUserAccess s6 = new SystemRoleUserAccess();
        s6.systemRoleID = clientUser.id;
        s6.userID = liCu.id;
        s6.uuid = hudsonBank.id;
        sruas.saveSystemRoleUserAccess(s6);

        SystemRoleUserAccess s7 = new SystemRoleUserAccess();
        s7.systemRoleID = clientUser.id;
        s7.userID = deMi.id;
        s7.uuid = maristSecurity.id;
        sruas.saveSystemRoleUserAccess(s7);

        SystemRoleUserAccess s8 = new SystemRoleUserAccess();
        s8.systemRoleID = clientBeaconManager.id;
        s8.userID = raGr.id;
        s8.uuid = hudsonBank.id;
        sruas.saveSystemRoleUserAccess(s8);

        SystemRoleUserAccess s9 = new SystemRoleUserAccess();
        s9.systemRoleID = clientBeaconManager.id;
        s9.userID = evRi.id;
        s9.uuid = hudsonBank.id;
        sruas.saveSystemRoleUserAccess(s9);

        SystemRoleUserAccess s11 = new SystemRoleUserAccess();
        s11.systemRoleID = clientManager.id;
        s11.userID = viJo.id;
        s11.uuid = maristSecurity.id;
        sruas.saveSystemRoleUserAccess(s11);

        SystemRoleUserAccess s12 = new SystemRoleUserAccess();
        s12.systemRoleID = clientManager.id;
        s12.userID = jaRa.id;
        s12.uuid = hudsonBank.id;
        sruas.saveSystemRoleUserAccess(s12);

        SystemRoleUserAccess s13 = new SystemRoleUserAccess();
        s13.systemRoleID = clientManager.id;
        s13.userID = keMo.id;
        s13.uuid = maristSecurity.id;
        sruas.saveSystemRoleUserAccess(s13);

        SystemRoleUserAccess s14 = new SystemRoleUserAccess();
        s14.systemRoleID = clientMapViewer.id;
        s14.userID = erFu.id;
        s14.uuid = hudsonBank.id;
        sruas.saveSystemRoleUserAccess(s14);

        SystemRoleUserAccess s15 = new SystemRoleUserAccess();
        s15.systemRoleID = clientMapViewer.id;
        s15.userID = amMo.id;
        s15.uuid = maristSecurity.id;
        sruas.saveSystemRoleUserAccess(s15);

        SystemRoleUserAccess s16 = new SystemRoleUserAccess();
        s16.systemRoleID = clientMapViewer.id;
        s16.userID = gaRo.id;
        s16.uuid = hudsonBank.id;
        sruas.saveSystemRoleUserAccess(s16);

        SystemRoleUserAccess s17 = new SystemRoleUserAccess();
        s17.systemRoleID = clientMapViewer.id;
        s17.userID = vaRi.id;
        s17.uuid = maristSecurity.id;
        sruas.saveSystemRoleUserAccess(s17);

        SystemRoleUserAccess s18 = new SystemRoleUserAccess();
        s18.systemRoleID = clientUser.id;
        s18.userID = caPa.id;
        s18.uuid = hudsonBank.id;
        sruas.saveSystemRoleUserAccess(s18);

        SystemRoleUserAccess s19 = new SystemRoleUserAccess();
        s19.systemRoleID = clientUser.id;
        s19.userID = kaSe.id;
        s19.uuid = maristSecurity.id;
        sruas.saveSystemRoleUserAccess(s19);

        SystemRoleUserAccess s20 = new SystemRoleUserAccess();
        s20.systemRoleID = clientUser.id;
        s20.userID = keMo.id;
        s20.uuid = hudsonBank.id;
        sruas.saveSystemRoleUserAccess(s20);

        SystemRoleUserAccess s21 = new SystemRoleUserAccess();
        s21.systemRoleID = clientUser.id;
        s21.userID = caPa.id;
        s21.uuid = maristSecurity.id;
        sruas.saveSystemRoleUserAccess(s21);

        SystemRoleUserAccess s22 = new SystemRoleUserAccess();
        s22.systemRoleID = clientUser.id;
        s22.userID = roBi.id;
        s22.uuid = hudsonBank.id;
        sruas.saveSystemRoleUserAccess(s22);

        SystemRoleUserAccess s23 = new SystemRoleUserAccess();
        s23.systemRoleID = clientUser.id;
        s23.userID = roBi.id;
        s23.uuid = maristSecurity.id;
        sruas.saveSystemRoleUserAccess(s23);

        SystemRoleUserAccess s24 = new SystemRoleUserAccess();
        s24.systemRoleID = clientAdministrator.id;
        s24.userID = kaSe.id;
        s24.uuid = maristSecurity.id;
        sruas.saveSystemRoleUserAccess(s24);

        SystemRoleUserAccess s25 = new SystemRoleUserAccess();
        s25.systemRoleID = clientAdministrator.id;
        s25.userID = gaRo.id;
        s25.uuid = maristSecurity.id;
        sruas.saveSystemRoleUserAccess(s25);

        ClientRoleUserAccess c26 = new ClientRoleUserAccess();
        c26.clientRoleID = maintenanceStaff.id;
        c26.userID = liCu.id;
        c26.uuid = maristSecurity.id;
        cruas.saveClientRoleUserAccess(c26);

        ClientRoleUserAccess c27 = new ClientRoleUserAccess();
        c27.clientRoleID = maintenanceStaff.id;
        c27.userID = raGr.id;
        c27.uuid = maristSecurity.id;
        cruas.saveClientRoleUserAccess(c27);

        ClientRoleUserAccess c28 = new ClientRoleUserAccess();
        c28.clientRoleID = analyticsRole.id;
        c28.userID = keMo.id;
        c28.uuid = maristSecurity.id;
        cruas.saveClientRoleUserAccess(c28);

        ClientRoleUserAccess c29 = new ClientRoleUserAccess();
        c29.clientRoleID = analyticsRole.id;
        c29.userID = viJo.id;
        c29.uuid = maristSecurity.id;
        cruas.saveClientRoleUserAccess(c29);

        ClientRoleUserAccess c30 = new ClientRoleUserAccess();
        c30.clientRoleID = maintenanceStaff.id;
        c30.userID = vaRi.id;
        c30.uuid = maristSecurity.id;
        cruas.saveClientRoleUserAccess(c30);

        //Modify Records
        Thread.sleep(3000);

        vaRiAddress.addressLine1 = "82 Rarrow Dr";
        vaRiAddress.addressLine2 = "Appt 54";
        vaRiAddress = as.saveAddress(vaRiAddress);

        alFi.userLastName = "Burry";
        alFi = us.saveUsers(alFi);

        kaSe.userWeight = 150;
        kaSe = us.saveUsers(kaSe);

        raGrCard.userIdDateIssue = new Date(2020, 11, 12);
        raGrCard.userIdDateExpire = new Date(2023, 2,1);
        raGrCard = uis.saveUserIdentity(raGrCard);

        caHa.userEmail = "Ca235252@yahoo.com";
        caHa.userPassword = "3gvw4tg3wb2tg";
        caHa = us.saveUsers(caHa);

        //adding locations
        Address downtownLocationAddress = new Address();
        downtownLocationAddress.addressLine1 = "124 Main St";
        downtownLocationAddress.addressCity = "Poughkeepsie";
        downtownLocationAddress.addressState = "New York";
        downtownLocationAddress.addressCountry = "United States";
        downtownLocationAddress.addressZIP = "13590";
        downtownLocationAddress = as.saveAddress(downtownLocationAddress);

        Location downtownLocation = new Location();
        downtownLocation.minLatitude = 100.12;
        downtownLocation.maxLatitude = 120.51;
        downtownLocation.minLongitude = 134.64;
        downtownLocation.maxLongitude =  153.25;
        downtownLocation.uuid = hudsonBank.id;
        downtownLocation.addressID = downtownLocationAddress.id;
        downtownLocation = ls.saveLocation(downtownLocation);

        Floor downtownFloor1 = new Floor();
        downtownFloor1.minLatitude = 100.12;
        downtownFloor1.maxLatitude = 120.51;
        downtownFloor1.minLongitude = 134.64;
        downtownFloor1.maxLongitude =  153.25;
        downtownFloor1.minAltitude = 10.0;
        downtownFloor1.maxAltitude = 18.0;
        downtownFloor1.majorID = downtownLocation.id;
        downtownFloor1 = fs.saveFloor(downtownFloor1);

        Address primaryLocationAddress = new Address();
        primaryLocationAddress.addressLine1 = "73 Maple St";
        primaryLocationAddress.addressCity = "Poughkeepsie";
        primaryLocationAddress.addressState = "New York";
        primaryLocationAddress.addressCountry = "United States";
        primaryLocationAddress.addressZIP = "13590";
        primaryLocationAddress = as.saveAddress(primaryLocationAddress);

        Location primaryLocation = new Location();
        primaryLocation.minLatitude = 350.36;
        primaryLocation.maxLatitude = 485.32;
        primaryLocation.minLongitude = 240.21;
        primaryLocation.maxLongitude =  260.1;
        primaryLocation.uuid = hudsonBank.id;
        primaryLocation.addressID = primaryLocationAddress.id;
        primaryLocation = ls.saveLocation(primaryLocation);

        Floor primaryFloor1 = new Floor();
        primaryFloor1.minLatitude = 350.36;
        primaryFloor1.maxLatitude = 485.32;
        primaryFloor1.minLongitude = 240.21;
        primaryFloor1.maxLongitude =  260.1;
        primaryFloor1.minAltitude = 102.1;
        primaryFloor1.maxAltitude = 110.2;
        primaryFloor1.majorID = downtownLocation.id;
        primaryFloor1 = fs.saveFloor(downtownFloor1);

        Floor primaryFloor2 = new Floor();
        primaryFloor2.minLatitude = 350.36;
        primaryFloor2.maxLatitude = 485.32;
        primaryFloor2.minLongitude = 240.21;
        primaryFloor2.maxLongitude =  260.1;
        primaryFloor2.minAltitude = 110.3;
        primaryFloor2.maxAltitude = 120.5;
        primaryFloor2.majorID = downtownLocation.id;
        primaryFloor2 = fs.saveFloor(primaryFloor2);

        Address studentCenterLocationAddress = new Address();
        studentCenterLocationAddress.addressLine1 = "20 North Rd";
        studentCenterLocationAddress.addressCity = "Poughkeepsie";
        studentCenterLocationAddress.addressState = "New York";
        studentCenterLocationAddress.addressCountry = "United States";
        studentCenterLocationAddress.addressZIP = "13590";
        studentCenterLocationAddress = as.saveAddress(studentCenterLocationAddress);

        Location studentCenterLocation = new Location();
        studentCenterLocation.minLatitude = 50.25;
        studentCenterLocation.maxLatitude = 200.51;
        studentCenterLocation.minLongitude = 500.64;
        studentCenterLocation.maxLongitude =  730.25;
        studentCenterLocation.uuid = maristSecurity.id;
        studentCenterLocation.addressID = studentCenterLocationAddress.id;
        studentCenterLocation = ls.saveLocation(studentCenterLocation);

        Floor studentCenterFloor1 = new Floor();
        studentCenterFloor1.minLatitude = 50.25;
        studentCenterFloor1.maxLatitude = 200.51;
        studentCenterFloor1.minLongitude = 500.64;
        studentCenterFloor1.maxLongitude =  730.25;
        studentCenterFloor1.minAltitude = 10.0;
        studentCenterFloor1.maxAltitude = 18.0;
        studentCenterFloor1.majorID = studentCenterLocation.id;
        studentCenterFloor1 = fs.saveFloor(studentCenterFloor1);

        Address dysonLocationAddress = new Address();
        dysonLocationAddress.addressLine1 = "20 North Rd";
        dysonLocationAddress.addressCity = "Poughkeepsie";
        dysonLocationAddress.addressState = "New York";
        dysonLocationAddress.addressCountry = "United States";
        dysonLocationAddress.addressZIP = "13590";
        dysonLocationAddress = as.saveAddress(dysonLocationAddress);

        Location dysonLocation = new Location();
        dysonLocation.minLatitude = 80.25;
        dysonLocation.maxLatitude = 150.34;
        dysonLocation.minLongitude = 600.36;
        dysonLocation.maxLongitude =  832.63;
        dysonLocation.uuid = maristSecurity.id;
        dysonLocation.addressID = dysonLocationAddress.id;
        dysonLocation = ls.saveLocation(dysonLocation);

        Floor dysonFloor1 = new Floor();
        dysonFloor1.minLatitude = 80.25;
        dysonFloor1.maxLatitude = 150.34;
        dysonFloor1.minLongitude = 600.36;
        dysonFloor1.maxLongitude =  832.63;
        dysonFloor1.minAltitude = 15.0;
        dysonFloor1.maxAltitude = 24.5;
        dysonFloor1.majorID = dysonLocation.id;
        dysonFloor1 = fs.saveFloor(dysonFloor1);

        Floor dysonFloor2 = new Floor();
        dysonFloor2.minLatitude = 80.25;
        dysonFloor2.maxLatitude = 150.34;
        dysonFloor2.minLongitude = 600.36;
        dysonFloor2.maxLongitude =  810.41;
        dysonFloor2.minAltitude = 24.6;
        dysonFloor2.maxAltitude = 32.5;
        dysonFloor2.majorID = dysonLocation.id;
        dysonFloor2 = fs.saveFloor(dysonFloor2);

        Floor dysonFloor3 = new Floor();
        dysonFloor3.minLatitude = 80.25;
        dysonFloor3.maxLatitude = 150.34;
        dysonFloor3.minLongitude = 600.36;
        dysonFloor3.maxLongitude =  810.41;
        dysonFloor3.minAltitude = 32.6;
        dysonFloor3.maxAltitude = 40.2;
        dysonFloor3.majorID = dysonLocation.id;
        dysonFloor3 = fs.saveFloor(dysonFloor3);


        //Beacons
        Beacon downtownB1 = new Beacon();
        downtownB1.longitude = 135.23;
        downtownB1.latitude = 115.63;
        downtownB1.altitude = 17.5;
        downtownB1.floorID = downtownFloor1.id;
        downtownB1.majorID = downtownLocation.id;
        downtownB1 = bs.saveBeacon(downtownB1);

        Beacon downtownB2 = new Beacon();
        downtownB2.longitude = 142.62;
        downtownB2.latitude = 111.72;
        downtownB2.altitude = 17.5;
        downtownB2.floorID = downtownFloor1.id;
        downtownB2.majorID = downtownLocation.id;
        downtownB2 = bs.saveBeacon(downtownB2);

        Beacon downtownB3 = new Beacon();
        downtownB3.longitude = 151.56;
        downtownB3.latitude = 103.35;
        downtownB3.altitude = 17.5;
        downtownB3.floorID = downtownFloor1.id;
        downtownB3.majorID = downtownLocation.id;
        downtownB3 = bs.saveBeacon(downtownB3);

        Beacon downtownB4 = new Beacon();
        downtownB4.longitude = 148.35;
        downtownB4.latitude = 104.62;
        downtownB4.altitude = 17.5;
        downtownB4.floorID = downtownFloor1.id;
        downtownB4.majorID = downtownLocation.id;
        downtownB4 = bs.saveBeacon(downtownB4);

        Beacon downtownB5 = new Beacon();
        downtownB5.longitude = 141.83;
        downtownB5.latitude = 117.92;
        downtownB5.altitude = 17.5;
        downtownB5.floorID = downtownFloor1.id;
        downtownB5.majorID = downtownLocation.id;
        downtownB5 = bs.saveBeacon(downtownB5);

        Beacon downtownB6 = new Beacon();
        downtownB6.longitude = 139.72;
        downtownB6.latitude = 113.61;
        downtownB6.altitude = 17.5;
        downtownB6.floorID = downtownFloor1.id;
        downtownB6.majorID = downtownLocation.id;
        downtownB6 = bs.saveBeacon(downtownB6);

        Beacon downtownB7 = new Beacon();
        downtownB7.longitude = 153.43;
        downtownB7.latitude = 108.30;
        downtownB7.altitude = 17.5;
        downtownB7.floorID = downtownFloor1.id;
        downtownB7.majorID = downtownLocation.id;
        downtownB7 = bs.saveBeacon(downtownB7);

        Beacon primaryB8 = new Beacon();
        primaryB8.longitude = 250.53;
        primaryB8.latitude = 372.24;
        primaryB8.altitude = 109.5;
        primaryB8.floorID = primaryFloor1.id;
        primaryB8.majorID = primaryLocation.id;
        primaryB8 = bs.saveBeacon(primaryB8);

        Beacon primaryB9 = new Beacon();
        primaryB9.longitude = 248.82;
        primaryB9.latitude = 372.24;
        primaryB9.altitude = 109.5;
        primaryB9.floorID = primaryFloor1.id;
        primaryB9.majorID = primaryLocation.id;
        primaryB9 = bs.saveBeacon(primaryB9);

        Beacon primaryB10 = new Beacon();
        primaryB10.longitude = 252.47;
        primaryB10.latitude = 427.73;
        primaryB10.altitude = 109.5;
        primaryB10.floorID = primaryFloor1.id;
        primaryB10.majorID = primaryLocation.id;
        primaryB10 = bs.saveBeacon(primaryB10);

        Beacon primaryB11 = new Beacon();
        primaryB11.longitude = 243.18;
        primaryB11.latitude = 353.82;
        primaryB11.altitude = 109.5;
        primaryB11.floorID = primaryFloor1.id;
        primaryB11.majorID = primaryLocation.id;
        primaryB11 = bs.saveBeacon(primaryB11);

        Beacon primaryB12 = new Beacon();
        primaryB12.longitude = 258.29;
        primaryB12.latitude = 452.49;
        primaryB12.altitude = 109.5;
        primaryB12.floorID = primaryFloor1.id;
        primaryB12.majorID = primaryLocation.id;
        primaryB12 = bs.saveBeacon(primaryB12);

        Beacon primaryB13 = new Beacon();
        primaryB13.longitude = 256.73;
        primaryB13.latitude = 393.40;
        primaryB13.altitude = 109.5;
        primaryB13.floorID = primaryFloor1.id;
        primaryB13.majorID = primaryLocation.id;
        primaryB13 = bs.saveBeacon(primaryB13);

        Beacon primaryB14 = new Beacon();
        primaryB14.longitude = 251.52;
        primaryB14.latitude = 425.28;
        primaryB14.altitude = 109.5;
        primaryB14.floorID = primaryFloor1.id;
        primaryB14.majorID = primaryLocation.id;
        primaryB14 = bs.saveBeacon(primaryB14);

        Beacon primaryB15 = new Beacon();
        primaryB15.longitude = 248.82;
        primaryB15.latitude = 372.24;
        primaryB15.altitude = 120.0;
        primaryB15.floorID = primaryFloor2.id;
        primaryB15.majorID = primaryLocation.id;
        primaryB15 = bs.saveBeacon(primaryB15);

        Beacon primaryB16 = new Beacon();
        primaryB16.longitude = 252.47;
        primaryB16.latitude = 427.73;
        primaryB16.altitude = 120.0;
        primaryB16.floorID = primaryFloor2.id;
        primaryB16.majorID = primaryLocation.id;
        primaryB16 = bs.saveBeacon(primaryB16);

        Beacon primaryB17 = new Beacon();
        primaryB17.longitude = 243.18;
        primaryB17.latitude = 353.82;
        primaryB17.altitude = 120.0;
        primaryB17.floorID = primaryFloor2.id;
        primaryB17.majorID = primaryLocation.id;
        primaryB17 = bs.saveBeacon(primaryB17);

        Beacon primaryB18 = new Beacon();
        primaryB18.longitude = 258.29;
        primaryB18.latitude = 452.49;
        primaryB18.altitude = 120.0;
        primaryB18.floorID = primaryFloor2.id;
        primaryB18.majorID = primaryLocation.id;
        primaryB18 = bs.saveBeacon(primaryB18);

        Beacon primaryB19 = new Beacon();
        primaryB19.longitude = 256.73;
        primaryB19.latitude = 393.40;
        primaryB19.altitude = 120.0;
        primaryB19.floorID = primaryFloor2.id;
        primaryB19.majorID = primaryLocation.id;
        primaryB19 = bs.saveBeacon(primaryB19);

        Beacon primaryB20 = new Beacon();
        primaryB20.longitude = 251.52;
        primaryB20.latitude = 425.28;
        primaryB20.altitude = 120.0;
        primaryB20.floorID = primaryFloor2.id;
        primaryB20.majorID = primaryLocation.id;
        primaryB20 = bs.saveBeacon(primaryB20);


        Beacon studentCenterB1 = new Beacon();
        studentCenterB1.longitude = 550.24;
        studentCenterB1.latitude = 140.74;
        studentCenterB1.altitude = 17.0;
        studentCenterB1.floorID = studentCenterFloor1.id;
        studentCenterB1.majorID = studentCenterLocation.id;
        studentCenterB1 = bs.saveBeacon(studentCenterB1);

        Beacon studentCenterB2 = new Beacon();
        studentCenterB2.longitude = 527.83;
        studentCenterB2.latitude = 140.74;
        studentCenterB2.altitude = 17.0;
        studentCenterB2.floorID = studentCenterFloor1.id;
        studentCenterB2.majorID = studentCenterLocation.id;
        studentCenterB2 = bs.saveBeacon(studentCenterB2);

        Beacon studentCenterB3 = new Beacon();
        studentCenterB3.longitude = 589.24;
        studentCenterB3.latitude = 73.83;
        studentCenterB3.altitude = 15.0;
        studentCenterB3.floorID = studentCenterFloor1.id;
        studentCenterB3.majorID = studentCenterLocation.id;
        studentCenterB3 = bs.saveBeacon(studentCenterB3);

        Beacon studentCenterB4 = new Beacon();
        studentCenterB4.longitude = 682.18;
        studentCenterB4.latitude = 126.73;
        studentCenterB4.altitude = 15.0;
        studentCenterB4.floorID = studentCenterFloor1.id;
        studentCenterB4.majorID = studentCenterLocation.id;
        studentCenterB4 = bs.saveBeacon(studentCenterB4);

        Beacon studentCenterB5 = new Beacon();
        studentCenterB5.longitude = 726.35;
        studentCenterB5.latitude = 67.15;
        studentCenterB5.altitude = 17.0;
        studentCenterB5.floorID = studentCenterFloor1.id;
        studentCenterB5.majorID = studentCenterLocation.id;
        studentCenterB5 = bs.saveBeacon(studentCenterB5);


        Beacon dysonB6 = new Beacon();
        dysonB6.longitude = 630.26;
        dysonB6.latitude = 97.15;
        dysonB6.altitude = 24.0;
        dysonB6.floorID = dysonFloor1.id;
        dysonB6.majorID = dysonLocation.id;
        dysonB6 = bs.saveBeacon(dysonB6);

        Beacon dysonB7 = new Beacon();
        dysonB7.longitude = 714.95;
        dysonB7.latitude = 123.63;
        dysonB7.altitude = 24.0;
        dysonB7.floorID = dysonFloor1.id;
        dysonB7.majorID = dysonLocation.id;
        dysonB7 = bs.saveBeacon(dysonB7);

        Beacon dysonB8 = new Beacon();
        dysonB8.longitude = 720.25;
        dysonB8.latitude = 83.27;
        dysonB8.altitude = 24.0;
        dysonB8.floorID = dysonFloor1.id;
        dysonB8.majorID = dysonLocation.id;
        dysonB8 = bs.saveBeacon(dysonB8);

        Beacon dysonB9 = new Beacon();
        dysonB9.longitude = 662.92;
        dysonB9.latitude = 104.73;
        dysonB9.altitude = 24.0;
        dysonB9.floorID = dysonFloor1.id;
        dysonB9.majorID = dysonLocation.id;
        dysonB9 = bs.saveBeacon(dysonB9);

        Beacon dysonB10 = new Beacon();
        dysonB10.longitude = 830.53;
        dysonB10.latitude = 148.35;
        dysonB10.altitude = 24.0;
        dysonB10.floorID = dysonFloor1.id;
        dysonB10.majorID = dysonLocation.id;
        dysonB10 = bs.saveBeacon(dysonB10);


        Beacon dysonB11 = new Beacon();
        dysonB11.longitude = 692.38;
        dysonB11.latitude = 97.15;
        dysonB11.altitude = 32.0;
        dysonB11.floorID = dysonFloor2.id;
        dysonB11.majorID = dysonLocation.id;
        dysonB11 = bs.saveBeacon(dysonB11);

        Beacon dysonB12 = new Beacon();
        dysonB12.longitude = 724.89;
        dysonB12.latitude = 123.63;
        dysonB12.altitude = 32.0;
        dysonB12.floorID = dysonFloor2.id;
        dysonB12.majorID = dysonLocation.id;
        dysonB12 = bs.saveBeacon(dysonB12);

        Beacon dysonB13 = new Beacon();
        dysonB13.longitude = 803.25;
        dysonB13.latitude = 83.27;
        dysonB13.altitude = 32.0;
        dysonB13.floorID = dysonFloor2.id;
        dysonB13.majorID = dysonLocation.id;
        dysonB13 = bs.saveBeacon(dysonB13);

        Beacon dysonB14 = new Beacon();
        dysonB14.longitude = 648.75;
        dysonB14.latitude = 104.73;
        dysonB14.altitude = 32.0;
        dysonB14.floorID = dysonFloor2.id;
        dysonB14.majorID = dysonLocation.id;
        dysonB14 = bs.saveBeacon(dysonB14);

        Beacon dysonB15 = new Beacon();
        dysonB15.longitude = 629.27;
        dysonB15.latitude = 148.35;
        dysonB15.altitude = 32.0;
        dysonB15.floorID = dysonFloor2.id;
        dysonB15.majorID = dysonLocation.id;
        dysonB15 = bs.saveBeacon(dysonB15);

        Beacon dysonB16 = new Beacon();
        dysonB16.longitude = 692.38;
        dysonB16.latitude = 97.15;
        dysonB16.altitude = 39.5;
        dysonB16.floorID = dysonFloor3.id;
        dysonB16.majorID = dysonLocation.id;
        dysonB16 = bs.saveBeacon(dysonB16);

        Beacon dysonB17 = new Beacon();
        dysonB17.longitude = 724.89;
        dysonB17.latitude = 123.63;
        dysonB17.altitude = 39.5;
        dysonB17.floorID = dysonFloor3.id;
        dysonB17.majorID = dysonLocation.id;
        dysonB17 = bs.saveBeacon(dysonB17);

        Beacon dysonB18 = new Beacon();
        dysonB18.longitude = 803.25;
        dysonB18.latitude = 83.27;
        dysonB18.altitude = 39.5;
        dysonB18.floorID = dysonFloor3.id;
        dysonB18.majorID = dysonLocation.id;
        dysonB18 = bs.saveBeacon(dysonB18);

        Beacon dysonB19 = new Beacon();
        dysonB19.longitude = 648.75;
        dysonB19.latitude = 104.73;
        dysonB19.altitude = 39.5;
        dysonB19.floorID = dysonFloor3.id;
        dysonB19.majorID = dysonLocation.id;
        dysonB19 = bs.saveBeacon(dysonB19);

        Beacon dysonB20 = new Beacon();
        dysonB20.longitude = 629.27;
        dysonB20.latitude = 148.35;
        dysonB20.altitude = 39.5;
        dysonB20.floorID = dysonFloor3.id;
        dysonB20.majorID = dysonLocation.id;
        dysonB20 = bs.saveBeacon(dysonB20);

        //Guests
        Guest alFiHudVisit = new Guest();
        alFiHudVisit.userID = alFi.id;
        alFiHudVisit.guestCheckInTime = new Date();
        alFiHudVisit.majorID = primaryLocation.id;
        alFiHudVisit.guestState = GuestState.ARRIVED;
        alFiHudVisit = gs.saveGuest(alFiHudVisit);

        Guest keMoMaristVisit = new Guest();
        keMoMaristVisit.userID = keMo.id;
        keMoMaristVisit.guestCheckInTime = new Date();
        keMoMaristVisit.majorID = studentCenterLocation.id;
        keMoMaristVisit.guestDestination = "Mail Room";
        keMoMaristVisit.guestState = GuestState.ARRIVED;
        keMoMaristVisit = gs.saveGuest(keMoMaristVisit);

        Guest roBiMaristVisit = new Guest();
        roBiMaristVisit.userID = roBi.id;
        roBiMaristVisit.guestCheckInTime = new Date();
        roBiMaristVisit.majorID = dysonLocation.id;
        roBiMaristVisit.guestState = GuestState.ARRIVED;
        roBiMaristVisit =  gs.saveGuest(roBiMaristVisit);

        Guest caPaHudVisit = new Guest();
        caPaHudVisit.userID = caPa.id;
        caPaHudVisit.guestCheckInTime = new Date();
        caPaHudVisit.majorID = downtownLocation.id;
        caPaHudVisit.guestState = GuestState.ARRIVED;
        caPaHudVisit = gs.saveGuest(caPaHudVisit);

        Guest liCuHudVisit = new Guest();
        liCuHudVisit.userID = liCu.id;
        liCuHudVisit.guestCheckInTime = new Date();
        liCuHudVisit.guestDestination = "Teller Office";
        liCuHudVisit.majorID = primaryLocation.id;
        liCuHudVisit.guestState = GuestState.ARRIVED;
        liCuHudVisit = gs.saveGuest(liCuHudVisit);

        Guest viJoMaristVisit = new Guest();
        viJoMaristVisit.userID = viJo.id;
        viJoMaristVisit.guestCheckInTime = new Date();
        viJoMaristVisit.majorID = studentCenterLocation.id;
        viJoMaristVisit.guestDestination = "Main Office";
        viJoMaristVisit.guestState = GuestState.ARRIVED;
        viJoMaristVisit = gs.saveGuest(viJoMaristVisit);

        Thread.sleep(3000);

        Guest caHaMaristVisit = new Guest();
        caHaMaristVisit.userID = caHa.id;
        caHaMaristVisit.guestCheckInTime = new Date();
        caHaMaristVisit.majorID = dysonLocation.id;
        caHaMaristVisit.guestState = GuestState.ARRIVED;
        caHaMaristVisit =  gs.saveGuest(caHaMaristVisit);

        Guest erFuHudVisit = new Guest();
        erFuHudVisit.userID = erFu.id;
        erFuHudVisit.guestCheckInTime = new Date();
        erFuHudVisit.majorID = downtownLocation.id;
        erFuHudVisit.guestState = GuestState.ARRIVED;
        erFuHudVisit = gs.saveGuest(erFuHudVisit);

        alFiHudVisit.guestState = GuestState.CHECKED_IN;
        alFiHudVisit = gs.saveGuest(alFiHudVisit);

        viJoMaristVisit.guestState = GuestState.CHECKED_IN;
        viJoMaristVisit = gs.saveGuest(viJoMaristVisit);

        liCuHudVisit.guestState = GuestState.CHECKED_IN;
        liCuHudVisit = gs.saveGuest(liCuHudVisit);

        Thread.sleep(3000);

        alFiHudVisit.guestState = GuestState.CHECKED_OUT;
        alFiHudVisit = gs.saveGuest(alFiHudVisit);

        keMoMaristVisit.guestState = GuestState.CHECKED_IN;
        keMoMaristVisit = gs.saveGuest(keMoMaristVisit);

        Thread.sleep(300);

        viJoMaristVisit.guestState = GuestState.CHECKED_OUT;
        viJoMaristVisit = gs.saveGuest(viJoMaristVisit);

        Thread.sleep(3000);

        //QUERIES

        //Query 1
        System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        List<Client> clientList = cs.getListOfClients();
        for(Client client: clientList){
            System.out.println("Client ID: " + client.id + " Client Organization: " + client.clientOrgName);
            System.out.println("___________________");
        }

        //Query2
        for(Client client: clientList){
            List<ClientRoleUserAccess> cruaList = cruas.getListOfClientRoleUserAccessByUUID(client.id);
            List<SystemRoleUserAccess> sruaList = sruas.getListOfSystemRoleUserAccessByUUID(client.id);
            HashMap<Long, Integer> clientNoRepeats = new HashMap<>();
            for (ClientRoleUserAccess clientInRole: cruaList) {
                Users currentUser = us.getUsersById(clientInRole.userID);
                if (clientNoRepeats.get(currentUser.id) == null){
                    clientNoRepeats.put(currentUser.id,1);

                    System.out.println("Client ID: " + client.id + " Client Organization: " + client.clientOrgName +
                            " |UserID: " + currentUser.id + " User First Name: "+ currentUser.userFirstName +
                            " User Last Name: " + currentUser.userLastName);
                    System.out.println("___________________");
                }
            }
            for (SystemRoleUserAccess clientInRole: sruaList) {
                Users currentUser = us.getUsersById(clientInRole.userID);
                if (clientNoRepeats.get(currentUser.id) == null){
                    clientNoRepeats.put(currentUser.id,1);
                    System.out.println("Client ID: " + client.id + " Client Organization: " + client.clientOrgName +
                            " |UserID: " + currentUser.id + " User First Name: "+ currentUser.userFirstName +
                            " User Last Name: " + currentUser.userLastName);
                    System.out.println("___________________");
                }
            }
            System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        }

        //Query 3
        List<ClientRoleUserAccess> keMoCruaList = cruas.getListOfClientRoleUserAccessByUserID(keMo.id);
        List<SystemRoleUserAccess> keMoSruaList = sruas.getListOfSystemRoleUserAccessByUserID(keMo.id);
        for (ClientRoleUserAccess clientInRole: keMoCruaList) {
            Users currentUser = us.getUsersById(clientInRole.userID);
            ClientRole currentRole = crs.getClientRoleById(clientInRole.clientRoleID);
                System.out.println("UserID: " + currentUser.id + " User First Name: "+ currentUser.userFirstName +
                        " User Last Name: " + currentUser.userLastName + " |Role ID: " + currentRole.id +
                        " Role Name: " + currentRole.roleName);
                System.out.println("___________________");

        }
        for (SystemRoleUserAccess clientInRole: keMoSruaList) {
            Users currentUser = us.getUsersById(clientInRole.userID);
            SystemRole currentRole = srs.getSystemRoleById(clientInRole.systemRoleID);
            System.out.println("UserID: " + currentUser.id + " User First Name: "+ currentUser.userFirstName +
                    " User Last Name: " + currentUser.userLastName + " |Role ID: " + currentRole.id +
                    " Role Name: ");
            System.out.println("___________________");

        }
        System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        List<ClientRoleUserAccess> alFiCruaList = cruas.getListOfClientRoleUserAccessByUserID(alFi.id);
        List<SystemRoleUserAccess> alFiSruaList = sruas.getListOfSystemRoleUserAccessByUserID(alFi.id);

        for (ClientRoleUserAccess clientInRole: alFiCruaList) {
            Users currentUser = us.getUsersById(clientInRole.userID);
            ClientRole currentRole = crs.getClientRoleById(clientInRole.clientRoleID);
            System.out.println("UserID: " + currentUser.id + " User First Name: "+ currentUser.userFirstName +
                    " User Last Name: " + currentUser.userLastName + " |Role ID: " + currentRole.id +
                    " Role Name: ");
            System.out.println("___________________");

        }
        for (SystemRoleUserAccess clientInRole: alFiSruaList) {
            Users currentUser = us.getUsersById(clientInRole.userID);
            SystemRole currentRole = srs.getSystemRoleById(clientInRole.systemRoleID);
            System.out.println("UserID: " + currentUser.id + " User First Name: "+ currentUser.userFirstName +
                    " User Last Name: " + currentUser.userLastName + " |Role ID: " + currentRole.id +
                    " Role Name: ");
            System.out.println("___________________");

        }
        System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");


        //Query4
        FdfEntity<Users> vaRiHistory = us.getUsersWithHistoryById(vaRi.id);
        FdfEntity<Address> vaRiAddressHistory = as.getAddressWithHistoryById(vaRiHistory.current.addressID);
        System.out.println(" UserID: " + vaRiHistory.current.id + " User First Name: "+ vaRiHistory.current.userFirstName +
                " User Last Name: " + vaRiHistory.current.userLastName+ " |User Address ID: " + vaRiAddressHistory.current.id +
                " Address Line 1: " + vaRiAddressHistory.current.addressLine1 + " Address Line 2: "+ vaRiAddressHistory.current.addressLine2 +
                " Start time: " + vaRiAddressHistory.current.arsd + " End time: " + vaRiAddressHistory.current.ared);
        System.out.println("___________________");
        for(Address addressHistory: vaRiAddressHistory.history){
            System.out.println(" UserID: " + vaRiHistory.current.id + " User First Name: "+ vaRiHistory.current.userFirstName +
                    " User Last Name: " + vaRiHistory.current.userLastName+ " |User Address ID: " + addressHistory.id +
                    " Address Line 1: " + addressHistory.addressLine1 + " Address Line 2: "+ addressHistory.addressLine2 +
                    " Start time: " + addressHistory.arsd + " End time: " + addressHistory.ared);
            System.out.println("___________________");
        }
        System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        FdfEntity<Users> caHaHistory = us.getUsersWithHistoryById(caHa.id);
        System.out.println(" UserID: " + caHaHistory.current.id + " User First Name: "+ caHaHistory.current.userFirstName +
                " User Last Name: " + caHaHistory.current.userLastName+ " User Email: " + caHaHistory.current.userEmail +
                " User Password: " + caHaHistory.current.userPassword +
                " Start time: " + caHaHistory.current.arsd + " End time: " + caHaHistory.current.ared);
        System.out.println("___________________");
        for(Users userHistory: caHaHistory.history){
            System.out.println(" UserID: " + userHistory.id + " User First Name: "+ userHistory.userFirstName +
                    " User Last Name: " + userHistory.userLastName+ " User Email: " + userHistory.userEmail +
                    " User Password: " + userHistory.userPassword +
                    " Start time: " + userHistory.arsd + " End time: " + userHistory.ared);
            System.out.println("___________________");
        }
        System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        FdfEntity<Users> alFiHistory = us.getUsersWithHistoryById(alFi.id);
        System.out.println(" UserID: " + alFiHistory.current.id + " User First Name: "+ alFiHistory.current.userFirstName +
                " User Last Name: " + alFiHistory.current.userLastName +
                " Start time: " + alFiHistory.current.arsd + " End time: " + alFiHistory.current.ared);
        System.out.println("___________________");
        for(Users userHistory: alFiHistory.history){
            System.out.println(" UserID: " + userHistory.id + " User First Name: "+ userHistory.userFirstName +
                    " User Last Name: " + userHistory.userLastName +
                    " Start time: " + userHistory.arsd + " End time: " + userHistory.ared);
            System.out.println("___________________");
        }
        System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        FdfEntity<Users> kaSeHistory = us.getUsersWithHistoryById(kaSe.id);
        System.out.println(" UserID: " + kaSeHistory.current.id + " User First Name: "+ kaSeHistory.current.userFirstName +
                " User Last Name: " + kaSeHistory.current.userLastName + " User Weight: " + kaSeHistory.current.userWeight +
                " Start time: " + kaSeHistory.current.arsd + " End time: " + kaSeHistory.current.ared);
        System.out.println("___________________");
        for(Users userHistory: kaSeHistory.history){
            System.out.println(" UserID: " + userHistory.id + " User First Name: "+ userHistory.userFirstName +
                    " User Last Name: " + userHistory.userLastName + " User Weight: " + userHistory.userWeight +
                    " Start time: " + userHistory.arsd + " End time: " + userHistory.ared);
            System.out.println("___________________");
        }
        System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        FdfEntity<Users> raGrHistory = us.getUsersWithHistoryById(raGr.id);
        FdfEntity<UserIdentity> raGrIdHistory = uis.getUserIdentityWithHistoryById(raGrHistory.current.userIdentityID);
        System.out.println(" UserID: " + raGrHistory.current.id + " User First Name: "+ raGrHistory.current.userFirstName +
                " User Last Name: " + raGrHistory.current.userLastName+ " |User Identity ID: " + raGrIdHistory.current.id +
                " ID Issue Date: " + raGrIdHistory.current.userIdDateIssue + " Id Expiry Date: "+ raGrIdHistory.current.userIdDateExpire +
                " Start time: " + raGrIdHistory.current.arsd + " End time: " + raGrIdHistory.current.ared);
        System.out.println("___________________");
        for(UserIdentity userIdentityHistory: raGrIdHistory.history){
            System.out.println(" UserID: " + raGrHistory.current.id + " User First Name: "+ raGrHistory.current.userFirstName +
                    " User Last Name: " + raGrHistory.current.userLastName+ " |User Identity ID: " + userIdentityHistory.id +
                    " ID Issue Date: " + userIdentityHistory.userIdDateIssue + " Id Expiry Date: "+ userIdentityHistory.userIdDateExpire +
                    " Start time: " + userIdentityHistory.arsd + " End time: " + userIdentityHistory.ared);
            System.out.println("___________________");
        }
        System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        //Query 5
        for(Client client: clientList){
            List<Location> locList = ls.getListOfLocationsByUUID(client.id);
            for (Location location: locList) {
                List<Guest> guestList = gs.getListOfGuestByLocation(location.id);
                HashMap<Long, Integer> noRepeats = new HashMap<>();
                for (Guest guest: guestList) {
                    Users currentUser = us.getUsersById(guest.userID);
                    if (noRepeats.get(guest.id) == null) {
                        System.out.println("Client ID: " + client.id + " Client Organization: " + client.clientOrgName +
                                " |UserID: " + currentUser.id + " User First Name: " + currentUser.userFirstName +
                                " User Last Name: " + currentUser.userLastName + " |Location ID: " + location.id +
                                " |Guest Visit ID: " + guest.id + " Guest Destination: " + guest.guestDestination +
                                " Guest Check-in Time: " + guest.guestCheckInTime + " Guest Status: " + guest.guestState);
                        System.out.println("___________________");
                    }

                }
            }

            System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        }

        //Query 6
        FdfEntity<Guest> alFiVisit = gs.getGuestByUserWithHistory(alFi.id);
        System.out.println("UserID: " + alFi.id + " User First Name: " + alFi.userFirstName +
                " User Last Name: " + alFi.userLastName +
                " |Guest Visit ID: " + alFiVisit.current.id + " Guest Destination: " + alFiVisit.current.guestDestination +
                " Guest Check-in Time: " + alFiVisit.current.guestCheckInTime + " Guest Status: " + alFiVisit.current.guestState +
                " Start time: " + alFiVisit.current.arsd + " End time: " + alFiVisit.current.ared);
        System.out.println("___________________");
        for(Guest visitHistory : alFiVisit.history) {
            System.out.println("UserID: " + alFi.id + " User First Name: " + alFi.userFirstName +
                    " User Last Name: " + alFi.userLastName +
                    " |Guest Visit ID: " + visitHistory.id + " Guest Destination: " + visitHistory.guestDestination +
                    " Guest Check-in Time: " + visitHistory.guestCheckInTime + " Guest Status: " + visitHistory.guestState +
                    " Start time: " + visitHistory.arsd + " End time: " + visitHistory.ared);
            System.out.println("___________________");
        }

        //Query 7
        for(Client client: clientList){
            List<Location> locList = ls.getListOfLocationsByUUID(client.id);
            for (Location location: locList) {
                List<Floor> floorList = fs.getListOfFloorsByLocation(location.id);
                for (Floor floor: floorList) {
                    List<Beacon> beaconList = bs.getListOfBeaconsByFloor(floor.id);
                    for(Beacon beacon: beaconList) {
                        System.out.println("Client ID: " + client.id + " Client Organization: " + client.clientOrgName +
                                " |Location ID: " + location.id + " |FloorID " + floor.id +
                                " |Beacon ID: " + beacon.id + " Beacon Latitude: " + beacon.latitude +
                                " Beacon Longitude: " + beacon.longitude + " Beacon Altitude: " + beacon.altitude);
                        System.out.println("___________________");

                    }
                }
            }

            System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        }

    }


}

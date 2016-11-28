package controls;

import java.util.List;
import entities.*;

import drivers.*;

public class ChocoTestDataController {
    
    public ChocoTestDataController(){
    }
    
    
    
    public boolean addTestDataTo(BillableRepository billableRepository){
            
            //yyyy-MM-dd'T'HH:mm:ssZ
            
            String[][] billableStringDataArray = new String[26][];

            billableStringDataArray[0] = new String[]{"1",   "111111111", "111111111", "111111", "1/27/2016", "2016-11-26T10:32:31Z", "23.12", "Test Billable! #1"};
            billableStringDataArray[1] = new String[]{"2",   "222222222", "222222222", "222222", "2/24/2016", "2016-11-27T14:01:10Z", "32.15", "Test Billable! #2"};
            billableStringDataArray[2] = new String[]{"3",   "333333333", "333333333", "333333", "3/24/2016", "2016-11-27T14:01:10Z", "32.15", "Test Billable! #3"};
            billableStringDataArray[3] = new String[]{"4",   "444444444", "444444444", "444444", "4/24/2016", "2016-11-27T14:01:10Z", "42.15", "Test Billable! #4"};
            billableStringDataArray[4] = new String[]{"5",   "555555555", "555555555", "555555", "5/27/2016", "2016-11-26T10:32:31Z", "53.15", "Test Billable! #5"};
            billableStringDataArray[5] = new String[]{"6",   "666666666", "666666666", "666666", "6/27/2016", "2016-11-26T10:32:31Z", "63.16", "Test Billable! #6"};
            billableStringDataArray[6] = new String[]{"7",   "777777777", "777777777", "777777", "7/27/2016", "2016-11-26T10:32:31Z", "73.17", "Test Billable! #7"};
            billableStringDataArray[7] = new String[]{"8",   "888888888", "888888888", "888888", "8/27/2016", "2016-11-26T10:32:31Z", "83.18", "Test Billable! #8"};
            billableStringDataArray[8] = new String[]{"9",   "999999999", "999999999", "999999", "9/27/2016", "2016-11-26T10:32:31Z", "93.19", "Test Billable! #9"};
            billableStringDataArray[9] = new String[]{"10",  "111111111", "111111111", "111111", "10/27/2016", "2016-11-26T10:32:31Z", "13.11", "Test Billable! #10"};
            billableStringDataArray[10] = new String[]{"11", "222222222", "222222222", "222222", "11/27/2016", "2016-11-26T10:32:31Z", "23.12", "Test Billable! #11"};
            billableStringDataArray[11] = new String[]{"12", "222222222", "222222222", "222222", "11/27/2016", "2016-11-26T10:32:31Z", "23.12", "Test Billable! #12"};
            billableStringDataArray[12] = new String[]{"13", "333333333", "333333333", "333333", "1/27/2016", "2016-11-26T10:32:31Z", "33.13", "Test Billable! #13"};
            billableStringDataArray[13] = new String[]{"14", "333333333", "333333333", "333333", "2/27/2016", "2016-11-26T10:32:31Z", "33.13", "Test Billable! #14"};
            billableStringDataArray[14] = new String[]{"15", "444444444", "444444444", "444444", "3/27/2016", "2016-11-26T10:32:31Z", "43.14", "Test Billable! #15"};
            billableStringDataArray[15] = new String[]{"16", "444444444", "444444444", "444444", "4/27/2016", "2016-11-26T10:32:31Z", "43.14", "Test Billable! #16"};
            billableStringDataArray[16] = new String[]{"17", "555555555", "555555555", "555555", "5/27/2016", "2016-11-26T10:32:31Z", "53.15", "Test Billable! #17"};
            billableStringDataArray[17] = new String[]{"18", "555555555", "555555555", "555555", "6/27/2016", "2016-11-26T10:32:31Z", "53.15", "Test Billable! #18"};
            billableStringDataArray[18] = new String[]{"19", "666666666", "666666666", "666666", "7/27/2016", "2016-11-26T10:32:31Z", "63.16", "Test Billable! #19"};
            billableStringDataArray[19] = new String[]{"20", "666666666", "666666666", "666666", "8/27/2016", "2016-11-26T10:32:31Z", "63.16", "Test Billable! #20"};
            billableStringDataArray[20] = new String[]{"21", "777777777", "777777777", "777777", "9/27/2016", "2016-11-26T10:32:31Z", "73.17", "Test Billable! #21"};
            billableStringDataArray[21] = new String[]{"22", "777777777", "777777777", "777777", "10/27/2016", "2016-11-26T10:32:31Z", "73.17", "Test Billable! #22"};
            billableStringDataArray[22] = new String[]{"23", "888888888", "888888888", "888888", "11/27/2016", "2016-11-26T10:32:31Z", "83.18", "Test Billable! #23"};
            billableStringDataArray[23] = new String[]{"24", "888888888", "888888888", "888888", "11/27/2016", "2016-11-26T10:32:31Z", "83.18", "Test Billable! #24"};
            billableStringDataArray[24] = new String[]{"25", "999999999", "999999999", "999999", "1/27/2016", "2016-11-26T10:32:31Z", "93.19", "Test Billable! #25"};
            billableStringDataArray[25] = new String[]{"26", "999999999", "999999999", "999999", "2/27/2016", "2016-11-26T10:32:31Z", "93.19", "Test Billable! #26"};


            if (billableRepository.findAll().size() > 0){
                return false;
            }
            
            for (int i = 0; i < billableStringDataArray.length; i++){
                Billable billableTestData = new Billable();
                
                billableTestData.setEntityBillableIdNumber(billableStringDataArray[i][0]);
                billableTestData.setMemberNumberService(billableStringDataArray[i][1]);
                billableTestData.setProviderNumberServicing(billableStringDataArray[i][2]);
                billableTestData.setServiceNumberServiced(billableStringDataArray[i][3]);
                billableTestData.setDateServiced(billableStringDataArray[i][4]);
                billableTestData.setDateServicedRecorded(billableStringDataArray[i][5]);
                billableTestData.setServiceCost(Double.parseDouble(billableStringDataArray[i][6]));
                billableTestData.setServiceComment(billableStringDataArray[i][7]);

                billableRepository.save(billableTestData);
            }

            return true;
    }
    
    public boolean addTestDataTo(ProviderRepository providerRepository){
            if (providerRepository.findAll().size() > 0){
                return false;
            }
            
            return true;
    }
    
    public boolean addTestDataTo(ServiceRepository serviceRepository){
            if (serviceRepository.findAll().size() > 0){
                return false;
            }
            return true;
    }
    
    public boolean addTestDataTo(UserRepository userRepository){
            if (userRepository.findAll().size() > 0){
                return false;
            }
            return true;
    }
}

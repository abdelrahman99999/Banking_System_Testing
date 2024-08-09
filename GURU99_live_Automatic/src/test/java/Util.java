
import org.testng.annotations.DataProvider;

public class Util {
    public static final String Base_URL="http://www.demo.guru99.com/V4/";
    public static final String User_Name="mngr582388";
    public static final String Password="esYdyre";
    public static final String Expected_title ="Guru99 Bank Manager HomePage";

    public static final String Invalid_User_Name="xxxxxxxx";
    public static final String Invalid_Password="yyyyyyy";
    public static final String Popup_Window="User or Password is not valid";

    @DataProvider(name = "LoginDataProvider")

    public Object[][] LoginData(){

        return new Object[][]

                {

                        {User_Name, Password },
                        {User_Name, "***" },
                        { "***", Password },
                        { "***", "***" }

                };

    }



}

package com.tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.pages.AdminPage;
import com.pages.DeleteUserPage;
import com.pages.EditUserPage;
import com.pages.LoginPage;
import com.pages.SearchPage;
import com.utilities.TestBase;
import org.testng.annotations.Test;

public class LoginTest extends TestBase {

    @Test
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.login(userName, password);
        
        page.waitForSelector("text=Dashboard", new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(30000));

        AdminPage adminPage = new AdminPage(page);

        String employeeName = "Thomas Kutty Benny";
        String newUsername = "Vivek7009";
        String newPassword = "Jellyfish@123";
        String confirmPassword = "Jellyfish@123";
        String userRole = "Admin";
        String status = "Enabled";

        adminPage.admininput(employeeName, newUsername, newPassword, confirmPassword, userRole, status);
        SearchPage searchPage = new SearchPage(page);
        searchPage.searchUser(newUsername, "Admin", "Enabled");
        
        EditUserPage editUserPage = new EditUserPage(page);
        editUserPage.editUserStatus("Disabled"); 

        DeleteUserPage deleteUserPage = new DeleteUserPage(page);
        deleteUserPage.deleteUser();

        



    }
}

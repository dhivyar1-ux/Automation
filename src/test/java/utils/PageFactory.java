package utils;

import pages.LoginModalPage;
import pages.LoginPage;
import pages.Dashboard.Administration.CreateDepartmentPage;
import pages.Dashboard.Administration.DepartmentPage;
import pages.Dashboard.Games.GamesPage;
import pages.Dashboard.Users.DashboardPage;
import pages.Dashboard.Users.AppAdmin.AppAdminDashboardPage;
import pages.Dashboard.Users.AppAdmin.AppAdminPage;
import pages.Dashboard.Users.AppAdmin.CreateAppAdminPage;
import pages.Dashboard.Users.ContentCreator.ContentCreatorDashboardPage;
import pages.Dashboard.Users.ContentCreator.ContentCreatorPage;
import pages.Dashboard.Users.ContentCreator.CreateContentCreatorPage;
import pages.Dashboard.Users.Faculty.CreateFacultyPage;
import pages.Dashboard.Users.Faculty.FacultyDashboardPage;
import pages.Dashboard.Users.Faculty.FacultyPage;
import pages.Dashboard.Users.StaffAdmin.CreateStaffAdminPage;
import pages.Dashboard.Users.StaffAdmin.StaffAdminDashboardPage;
import pages.Dashboard.Users.StaffAdmin.StaffAdminPage;
import pages.Dashboard.Users.Student.StudentDashboardPage;
import pages.Dashboard.Users.Student.StudentsPage;
import pages.Dashboard.Users.UserGroup.CreateUserGroupPage;
import pages.Dashboard.Users.UserGroup.UserGroupPage;

public class PageFactory {

    public LoginPage loginPage()          { return new LoginPage(); }
    public DashboardPage dashboardPage()  { return new DashboardPage(); }
    public StudentsPage dashboardStudentsPage() { return new StudentsPage(); }
    public StudentDashboardPage studentDashboardPage() { return new StudentDashboardPage(); }
    public LoginModalPage loginModalPage() { return new LoginModalPage(); }
    public AppAdminPage appAdminPage() { return new AppAdminPage(); }
    public AppAdminDashboardPage appAdminDashboardPage() { return new AppAdminDashboardPage(); }
    public CreateAppAdminPage createAppAdminPage() { return new CreateAppAdminPage(); }
    public FacultyPage dashboardFacultyPage() { return new FacultyPage(); }
    public CreateFacultyPage createFaculty() { return new CreateFacultyPage(); }
    public FacultyDashboardPage facultyDashboardPage() { return new FacultyDashboardPage(); }
    public StaffAdminPage usersStaffAdminPage() { return new StaffAdminPage(); }
    public CreateStaffAdminPage createStaffAdminPage() { return new CreateStaffAdminPage(); }
    public StaffAdminDashboardPage staffAdminDashboardPage() { return new StaffAdminDashboardPage(); }
    public DepartmentPage departmentPage() { return new DepartmentPage(); }
    public CreateDepartmentPage createDepartmentPage() { return new CreateDepartmentPage(); }
    public ContentCreatorPage contentCreatorPage() { return new ContentCreatorPage(); }
    public CreateContentCreatorPage createContentCreatorPage() { return new CreateContentCreatorPage(); }
    public ContentCreatorDashboardPage contentCreatorDashboardPage() { return new ContentCreatorDashboardPage(); }
    public UserGroupPage userGroupPage() { return new UserGroupPage(); }
    public CreateUserGroupPage createUserGroupPage() { return new CreateUserGroupPage(); }
    public GamesPage gamesPage() { return new GamesPage(); }

    // … other factories …
}
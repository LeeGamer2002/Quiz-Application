/*
 * Title: Online Quiz Application
 * Author: Kalith K
 * Created on: 11-12-2023
 * Last Modified Date: 18-12-2023
 * Reviewed by: ---
 * Reviewed on: ---
*/

package com.aspiresys;
import java.sql.SQLException;

public class QuizApplication{
	public static void main(String[] args) throws SQLException {
		ApplicationMenu applicationMenu=new ApplicationMenu();
		applicationMenu.openApplication();
	}

}
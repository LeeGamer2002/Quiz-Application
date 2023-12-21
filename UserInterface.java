package com.aspiresys;

import java.io.FileNotFoundException;
import java.sql.SQLException;

interface UserInterface {
    void login() throws SQLException, FileNotFoundException;
}
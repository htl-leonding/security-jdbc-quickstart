package at.htl.security.jdbc;

import io.agroal.api.AgroalDataSource;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Path("/api/public")
public class PublicResource {

    @Inject
    AgroalDataSource defaultDataSource;

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String publicResource(){
        try {
            Connection conn = defaultDataSource.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT id, username, password, role FROM test_user";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.printf("%d - %s - %s - %s\n",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "public";
    }
}

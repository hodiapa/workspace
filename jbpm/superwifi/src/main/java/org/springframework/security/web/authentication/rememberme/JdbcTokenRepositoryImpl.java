package org.springframework.security.web.authentication.rememberme;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.yazuo.superwifi.util.PropertyUtil;

/**
 * JDBC based persistent login token repository implementation.
 *
 * @author Luke Taylor
 * @since 2.0
 */
public class JdbcTokenRepositoryImpl extends JdbcDaoSupport implements PersistentTokenRepository {
    //~ Static fields/initializers =====================================================================================

	public static final String SCHEMA_NAME =PropertyUtil.getInstance().getProperty("schemaName");
    /** Default SQL for creating the database table to store the tokens */
    public static final String CREATE_TABLE_SQL =
            "create table "+SCHEMA_NAME+"."+"persistent_logins (username varchar(64) not null, series varchar(64) primary key, " +
                    "token varchar(64) not null, last_used timestamp not null)";
    /** The default SQL used by the <tt>getTokenBySeries</tt> query */
    public static final String DEF_TOKEN_BY_SERIES_SQL =
            "select username,series,token,last_used from "+SCHEMA_NAME+"."+"persistent_logins where series = ?";
    /** The default SQL used by <tt>createNewToken</tt> */
    public static final String DEF_INSERT_TOKEN_SQL =
            "insert into "+SCHEMA_NAME+"."+"persistent_logins (username, series, token, last_used) values(?,?,?,?)";
    /** The default SQL used by <tt>updateToken</tt> */
    public static final String DEF_UPDATE_TOKEN_SQL =
            "update "+SCHEMA_NAME+"."+"persistent_logins set token = ?, last_used = ? where series = ?";
    /** The default SQL used by <tt>removeUserTokens</tt> */
    public static final String DEF_REMOVE_USER_TOKENS_SQL =
            "delete from "+SCHEMA_NAME+"."+"persistent_logins where username = ?";

    //~ Instance fields ================================================================================================

    private String tokensBySeriesSql = DEF_TOKEN_BY_SERIES_SQL;
    private String insertTokenSql = DEF_INSERT_TOKEN_SQL;
    private String updateTokenSql = DEF_UPDATE_TOKEN_SQL;
    private String removeUserTokensSql = DEF_REMOVE_USER_TOKENS_SQL;
    private boolean createTableOnStartup;

    protected void initDao() {
        if (createTableOnStartup) {
            getJdbcTemplate().execute(CREATE_TABLE_SQL);
        }
    }

    public void createNewToken(PersistentRememberMeToken token) {
        getJdbcTemplate().update(insertTokenSql, token.getUsername(), token.getSeries(),
                token.getTokenValue(), token.getDate());
    }

    public void updateToken(String series, String tokenValue, Date lastUsed) {
        getJdbcTemplate().update(updateTokenSql, tokenValue, new Date(), series);
    }

    /**
     * Loads the token data for the supplied series identifier.
     *
     * If an error occurs, it will be reported and null will be returned (since the result should just be a failed
     * persistent login).
     *
     * @param seriesId
     * @return the token matching the series, or null if no match found or an exception occurred.
     */
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        try {
            return getJdbcTemplate().queryForObject(tokensBySeriesSql, new RowMapper<PersistentRememberMeToken>() {
                public PersistentRememberMeToken mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new PersistentRememberMeToken(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4));
                }
            }, seriesId);
        } catch(IncorrectResultSizeDataAccessException moreThanOne) {
            logger.error("Querying token for series '" + seriesId + "' returned more than one value. Series" +
                    " should be unique");
        } catch(DataAccessException e) {
            logger.error("Failed to load token for series " + seriesId, e);
        }

        return null;
    }

    public void removeUserTokens(String username) {
        getJdbcTemplate().update(removeUserTokensSql, username);
    }

    /**
     * Intended for convenience in debugging. Will create the persistent_tokens database table when the class
     * is initialized during the initDao method.
     *
     * @param createTableOnStartup set to true to execute the
     */
    public void setCreateTableOnStartup(boolean createTableOnStartup) {
        this.createTableOnStartup = createTableOnStartup;
    }
}

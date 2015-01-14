package wairoadc.godiscover;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import wairoadc.godiscover.dao.TypeDAO;
import wairoadc.godiscover.model.Type;

/**
 * Created by Lucas on 14/01/2015.
 */
public class TestDB extends AndroidTestCase {
    public void testOpenDB() throws Throwable{

        TypeDAO typeDAO = new TypeDAO(mContext);
        SQLiteDatabase db = typeDAO.open();
        assertEquals(true,db.isOpen());
        db.close();
    }
    public void testInsert() throws Throwable{
        TypeDAO typeDAO = new TypeDAO(mContext);
        Type type = new Type();
        type.setName("audio");
        typeDAO.open();
        Type nType = typeDAO.insertType(type);
        assertEquals(nType.getName(),"audio");
    }

}

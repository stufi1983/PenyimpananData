package id.web.hayat.latiful.penyimpanandata;

/**
 * Created by Login on 3/1/2018.
 */

public class DataRecord {
        public String nama, nim;
        public int id;

        public DataRecord(int id, String nama, String nim){
            this.id = id;
            this.nama = nama;
            this.nim = nim;
        }

    public DataRecord() {

    }
}

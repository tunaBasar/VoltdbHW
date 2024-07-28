
import org.voltdb.*;
import org.voltdb.client.*;
import java.io.IOException;
import java.util.ArrayList;

public class main {

    private final Client client;

    public main(String servers) throws Exception {
        client = ClientFactory.createClient();
        client.createConnection(servers);
    }

    public ArrayList<Subscriber> getAllCustomers() {
        try {

            ClientResponse response = client.callProcedure("getAllCustomers");

            if (response.getStatus() != ClientResponse.SUCCESS){
                System.err.println(response.getStatusString());
            }
            else {

                ArrayList<Subscriber> subscribers = new ArrayList<Subscriber>();
                VoltTable voltTable = response.getResults()[0];
                int counter = 0;

                while(voltTable.advanceRow()) {

                    VoltTableRow row = voltTable.fetchRow(counter);
                    counter++;

                    subscribers.add(new Subscriber(
                            (int) row.get("SUBSC_ID", VoltType.INTEGER),
                            row.getString("SUBSC_NAME"),
                            row.getString("SUBSC_SURNAME"),
                            row.getString("MSISDN"),
                            (int) row.get("TARIFF_ID", VoltType.INTEGER),
                            row.getTimestampAsLong("START_DATE")
                    ));
                }

                return subscribers;
            }



        } catch (NoConnectionsException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.getStackTrace();
        } catch (ProcCallException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
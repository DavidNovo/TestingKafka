package net.novogrodsky;

/**
 * Created by david.j.novogrodsky on 5/2/2014.
 */
import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class SimplePartitioner implements Partitioner {
    public SimplePartitioner (VerifiableProperties props) {
    }

    /*
    public int partition(String key, int a_numPartitions) {
        int partition = 0;
        int offset = key.lastIndexOf('.');
        if (offset > 0) {
            partition = Integer.parseInt( key.substring(offset+1)) % a_numPartitions;
        }
        return partition;
    }
*/

    public int partition(Object key, int a_numPartitions) {
        int partition = 0;
        String incomingKey = (String) key;
        int offset = 0;
        offset = incomingKey.lastIndexOf('.');
        if (offset > 0) {
            partition = Integer.parseInt( incomingKey.substring(offset+1)) % a_numPartitions;
        }
        return partition;
    }
}

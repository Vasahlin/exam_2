package workoutTracking;

import clients.GymMember;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileHandling {
    public static final String DATA_EXT = ".dat";
    public static final String INDEX_EXT = ".idx";
    public static final int INDEX_SIZE = Long.SIZE / 8;
    private static FileChannel index_file = null;
    private static FileChannel data_file = null;
    final static String fileName = String.format(
            "%s\\src\\workoutTracking\\workoutList", System.getProperty("user.dir"));


    protected static byte[] serializeWorkout(GymMember member) throws IOException {
        Workout workout = new Workout(
                member, member.getWorkoutHistory());

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream stream = new ObjectOutputStream(out)) {
            stream.writeObject(workout);
            stream.flush();
            return out.toByteArray();
        }
    }

    protected static Workout deserializeWorkout(byte[] array) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream read = new ByteArrayInputStream(array);
             ObjectInputStream in = new ObjectInputStream(read)) {
            return (Workout) in.readObject();
        }
    }

    public static long appendEntry(GymMember member) throws IOException {
        try (RandomAccessFile raf_1 = new RandomAccessFile(fileName + INDEX_EXT, "rw");
             RandomAccessFile raf_2 = new RandomAccessFile(fileName + DATA_EXT, "rw")) {

            index_file = raf_1.getChannel();
            index_file.force(true);
            data_file = raf_2.getChannel();
            data_file.force(true);

            // Calculate the data index for append to data
            // file and append its value to the index file.
            long byteOffset = index_file.size();
            long index = byteOffset / (long) INDEX_SIZE;
            long dataOffset = (int) data_file.size();
            ByteBuffer bb = ByteBuffer.allocate(INDEX_SIZE);
            bb.putLong(dataOffset);
            bb.flip();
            index_file.position(byteOffset);
            int writtenBytes = index_file.write(bb);

            if (writtenBytes == 0) {
                return -1;
            }

            // Append serialized object data to the data file.
            data_file.position(dataOffset);
            writtenBytes = data_file.write(ByteBuffer.wrap(serializeWorkout(member)));

            if (writtenBytes == 0) {
                return -1;
            }

            return index;
        }

        public Entry readEntry(long index)
            throws IOException, ClassNotFoundException {

            // Get the data index and -length from the index file.
            long byteOffset = index * (long) EntryFile.INDEX_SIZE;
            ByteBuffer bbi = ByteBuffer.allocate(EntryFile.INDEX_SIZE);
            fci.position(byteOffset);
            if (fci.read(bbi) == -1) {
                throw new IndexOutOfBoundsException("Specified index is out of range");
            }
            bbi.flip();
            long dataOffset = bbi.getLong();
            bbi.rewind();
            long dataOffsetNext;
            if (fci.read(bbi) == -1) {
                dataOffsetNext = fcd.size();
            } else {
                bbi.flip();
                dataOffsetNext = bbi.getLong();
            }
            int dataSize = (int) (dataOffsetNext - dataOffset);

            // Get the serialized object data in a byte array.
            byte[] se = new byte[dataSize];
            fcd.position(dataOffset);
            fcd.read(ByteBuffer.wrap(se));

            // Deserialize the byte array into an instantiated object.
            return Entry.deserialize(se);
        }
    }

}

}

package workoutTracking;

import clients.GymMember;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandling {
    public static final String DATA_EXT = ".dat";
    public static final String INDEX_EXT = ".idx";
    public static final int INDEX_SIZE = Long.SIZE / 8;
    private static FileChannel indexChannel;
    private static FileChannel dataChannel;
    final static String fileName = String.format(
            "%s\\src\\workoutTracking\\workoutList", System.getProperty("user.dir"));
    protected static boolean test;


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

    public static long appendWorkoutToFile(GymMember member, Path optionalTestPath) throws IOException {
        Path dataPath, indexPath;
        if (test) {
            dataPath = Paths.get(optionalTestPath + DATA_EXT);
            indexPath = Paths.get(optionalTestPath + INDEX_EXT);
        } else {
            dataPath = Paths.get(fileName + DATA_EXT);
            indexPath = Paths.get(fileName + INDEX_EXT);
        }

        try (RandomAccessFile raf_1 = new RandomAccessFile(indexPath.toString(), "rw");
             RandomAccessFile raf_2 = new RandomAccessFile(dataPath.toString(), "rw")) {

            indexChannel = raf_1.getChannel();
            indexChannel.force(true);
            dataChannel = raf_2.getChannel();
            dataChannel.force(true);

            // Calculate the data index for append to data file
            // & append its value to the index file.
            long byteOffset = indexChannel.size();
            long index = byteOffset / (long) INDEX_SIZE;
            long dataOffset = (int) dataChannel.size();
            ByteBuffer byteBuffer = ByteBuffer.allocate(INDEX_SIZE);
            byteBuffer.putLong(dataOffset);
            byteBuffer.flip();
            indexChannel.position(byteOffset);
            int writtenBytes = indexChannel.write(byteBuffer);

            if (writtenBytes == 0) {
                return -1;
            }

            dataChannel.position(dataOffset);
            writtenBytes = dataChannel.write(ByteBuffer.wrap(serializeWorkout(member)));

            if (writtenBytes == 0) {
                return -1;
            }

            return index;
        }
    }

    public static Workout readWorkoutFromFile(long index, Path optionalTestPath)
            throws IOException, ClassNotFoundException {
        Path dataPath, indexPath;
        if (test) {
            dataPath = Paths.get(optionalTestPath + DATA_EXT);
            indexPath = Paths.get(optionalTestPath + INDEX_EXT);
        } else {
            dataPath = Paths.get(fileName + DATA_EXT);
            indexPath = Paths.get(fileName + INDEX_EXT);
        }

        try (RandomAccessFile raf_1 = new RandomAccessFile(indexPath.toString(), "rw");
             RandomAccessFile raf_2 = new RandomAccessFile(dataPath.toString(), "rw")) {
            indexChannel = raf_1.getChannel();
            indexChannel.force(true);
            dataChannel = raf_2.getChannel();
            dataChannel.force(true);

            ByteBuffer buffer = ByteBuffer.allocate(INDEX_SIZE);

            long byteOffset = index * (long) INDEX_SIZE;
            indexChannel.position(byteOffset);
            if (indexChannel.read(buffer) == -1) {
                throw new IndexOutOfBoundsException("Specified index out of bounds");
            }
            buffer.flip();
            long dataOffset = buffer.getLong();
            buffer.rewind();

            long dataNextOffset;
            if (indexChannel.read(buffer) == -1) {
                dataNextOffset = dataChannel.size();
            } else {
                buffer.flip();
                dataNextOffset = buffer.getLong();
            }

            int dataSize = (int) (dataNextOffset - dataOffset);
            byte[] toSerialize = new byte[dataSize];
            dataChannel.position(dataOffset);
            dataChannel.read(ByteBuffer.wrap(toSerialize));

            return deserializeWorkout(toSerialize);
        }
    }
}


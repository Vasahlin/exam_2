package workoutTracking;

import clients.GymMember;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

public class FileHandling {
    public static final String DATA_EXT = ".dat";
    public static final String INDEX_EXT = ".idx";
    public static final int INDEX_SIZE = Long.SIZE / 8;
    private static FileChannel indexChannel;
    private static FileChannel dataChannel;
    final static String fileName = String.format(
            "%s\\src\\workoutTracking\\workoutList", System.getProperty("user.dir"));
    protected static boolean test;

    public static void updateWorkouts(GymMember member, LocalDateTime timeOfWorkout, Path optionalTestPath)
            throws IOException, ClassNotFoundException {
        Path path;
        int index;
        if (test) {
            path = optionalTestPath;
        } else {
            path = Paths.get(fileName);
        }
        WorkoutRegister register = new WorkoutRegister(member);
        long amountOfElements = amountOfElements(path);
        if (amountOfElements > 0) {
            ArrayList<WorkoutRegister> registeredWorkouts = readAllWorkouts(amountOfElements, path);
            index = registeredClientIndex(registeredWorkouts, member.getSocialSecurity());
            if (index > -1) {
                registeredWorkouts.get(index).workoutHistory.add(timeOfWorkout);
            } else {
                registeredWorkouts.add(register);
            }
            writeAllWorkouts(registeredWorkouts, path);
        } else {
            appendWorkoutToFile(register, path);
        }
    }

    protected static int registeredClientIndex(ArrayList<WorkoutRegister> list, long social) {
        int left = 0, right = list.size() - 1, mid;
        long midSocial;
        while (left <= right) {
            mid = left + (right - left) / 2;
            midSocial = list.get(mid).getSocialOfClient();

            if (midSocial == social) {
                return mid;
            } else if (midSocial < social) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    protected static void writeAllWorkouts(ArrayList<WorkoutRegister> workouts, Path optionalTestPath)
            throws IOException {
        Path path, dataPath, indexPath;
        if (test) {
            path = optionalTestPath;
            dataPath = Paths.get(optionalTestPath + DATA_EXT);
            indexPath = Paths.get(optionalTestPath + INDEX_EXT);
        } else {
            path = Paths.get(fileName);
            dataPath = Paths.get(fileName + DATA_EXT);
            indexPath = Paths.get(fileName + INDEX_EXT);
        }

        if (!workouts.isEmpty()) {
            Files.deleteIfExists(dataPath);
            Files.deleteIfExists(indexPath);
            for (WorkoutRegister workout : workouts) {
                appendWorkoutToFile(workout, path);
            }
        }
    }

    protected static ArrayList<WorkoutRegister> readAllWorkouts(long amountOfElements, Path optionalTestPath)
            throws IOException, ClassNotFoundException {
        Path path;
        if (test) {
            path = optionalTestPath;
        } else {
            path = Paths.get(fileName);
        }
        if ((--amountOfElements) >= 0) {
            ArrayList<WorkoutRegister> workouts = new ArrayList<>();
            for (long i = amountOfElements; i >= 0; i--) {
                workouts.add(readWorkoutFromFile(i, path));
            }
            workouts.sort(Comparator.comparingLong(WorkoutRegister::getSocialOfClient));
            return workouts;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    protected static long amountOfElements(Path optionalTestPath) throws IOException {
        Path indexPath;
        if (test) {
            indexPath = Paths.get(optionalTestPath + INDEX_EXT);
        } else {
            indexPath = Paths.get(fileName + INDEX_EXT);
        }

        try (RandomAccessFile raf_1 = new RandomAccessFile(indexPath.toString(), "r")) {
            indexChannel = raf_1.getChannel();
            indexChannel.force(true);
            long byteOffset = indexChannel.size();
            return byteOffset / (long) INDEX_SIZE;
        }
    }

    protected static byte[] serializeWorkout(WorkoutRegister workout) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream stream = new ObjectOutputStream(out)) {
            stream.writeObject(workout);
            stream.flush();
            return out.toByteArray();
        }
    }

    protected static WorkoutRegister deserializeWorkout(byte[] array) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream read = new ByteArrayInputStream(array);
             ObjectInputStream in = new ObjectInputStream(read)) {
            return (WorkoutRegister) in.readObject();
        }
    }

    public static long appendWorkoutToFile(WorkoutRegister workout, Path optionalTestPath) throws IOException {
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
            writtenBytes = dataChannel.write(ByteBuffer.wrap(serializeWorkout(workout)));

            if (writtenBytes == 0) {
                return -1;
            }

            workout.member.setFileIndex(index);
            return index;
        }
    }

    public static WorkoutRegister readWorkoutFromFile(long index, Path optionalTestPath)
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
            long offset = buffer.getLong();
            buffer.rewind();
            long nextOffset;
            if (indexChannel.read(buffer) == -1) {
                nextOffset = dataChannel.size();
            } else {
                buffer.flip();
                nextOffset = buffer.getLong();
            }

            int dataSize = (int) (nextOffset - offset);
            byte[] toDeserialize = new byte[dataSize];
            dataChannel.position(offset);
            dataChannel.read(ByteBuffer.wrap(toDeserialize));

            return deserializeWorkout(toDeserialize);
        }
    }
}


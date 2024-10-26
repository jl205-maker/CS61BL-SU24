package capers;

import java.io.*;

import static capers.Main.*;
import static capers.Utils.readContents;
import static capers.Utils.writeObject;

/** Represents a dog that can be serialized.
 * @author Sean Dooher
*/
public class Dog implements Serializable {

    /** Folder that dogs live in. */
    static final File DOG_FOLDER = Utils.join(CAPERS_FOLDER, "/dogs");;

    /** File extension for serialized dog files. */
    private static final String DOG_FILE_EXTENSION = ".dog";
    /**
     * Creates a dog object with the specified parameters.
     * @param name Name of dog
     * @param breed Breed of dog
     * @param age Age of dog
     */
    public Dog(String name, String breed, int age) {
        _age = age;
        _breed = breed;
        _name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        String fileName = CWD + name;
        File currentDogFile = new File(fileName);
        Dog d;

        if (!currentDogFile.exists()) {
            return null;
        }

        try {
            ObjectInputStream inp = new ObjectInputStream(new FileInputStream(currentDogFile));
            d = (Dog) inp.readObject();
            inp.close();
        } catch (IOException | ClassNotFoundException excp) {
            d = null;
        }

        return d;
    }

    /**
     * Increases a dog's age and celebrates!
     */
    public void haveBirthday() {
        _age += 1;
        System.out.println(toString());
        System.out.println("Happy birthday! Woof! Woof!");
    }

    /**
     * Saves a dog to a file for future use.
     */
    public void saveDog() {
        File DOG_FILE = Utils.join(DOGS_FOLDER, "/" + _name);
        writeObject(DOG_FILE, this);
    }

    @Override
    public String toString() {
        return String.format("Woof! My name is %s and I am a %s! I am %d years old! Woof!",
            _name, _breed, _age);
    }

    /** Age of dog. */
    private int _age;
    /** Breed of dog. */
    private String _breed;
    /** Name of dog. */
    private String _name;
}

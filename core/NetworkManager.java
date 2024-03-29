package prr.core;
import java.io.*;

import prr.core.exception.*;

//FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

  /**
   * The network itself.
   */
  private String _filename = "";
  private Network _network = new Network();


  public Network getNetwork() {
    return _network;
  }

  /**
   * @param filename name of the file containing the serialized application's state
   *                 to load.
   * @throws UnavailableFileException if the specified file does not exist or there is
   *                                  an error while processing this file.
   */
  public void load(String filename) throws UnavailableFileException, IOException, ClassNotFoundException {
    try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename))) {
      _network = (Network) input.readObject();
      _filename = filename;
    }catch(FileNotFoundException e){
      throw new UnavailableFileException(filename);
    }
  }

  /**
   * Saves the serialized application's state into the file associated to the current network.
   *
   * @throws FileNotFoundException           if for some reason the file cannot be created or opened.
   * @throws MissingFileAssociationException if the current network does not have a file.
   * @throws IOException                     if there is some error while serializing the state of the network to disk.
   */
  public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
    if (_filename == "") {
      throw new MissingFileAssociationException();
    }
      try(var fos = new FileOutputStream(_filename)){
        var byteArray = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteArray);
        out.writeObject(_network);
        fos.write(byteArray.toByteArray());
      } catch (FileNotFoundException e) {
        throw new FileNotFoundException();
      }
  }



  /**
   * Saves the serialized application's state into the specified file. The current network is
   * associated to this file.
   *
   * @param filename the name of the file.
   * @throws FileNotFoundException           if for some reason the file cannot be created or opened.
   * @throws MissingFileAssociationException if the current network does not have a file.
   * @throws IOException                     if there is some error while serializing the state of the network to disk.
   */
  public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
    _filename = filename;
    save();
  }

  /**
   * Read text input file and create domain entities..
   *
   * @param filename name of the text input file
   * @throws ImportFileException
   */

  public void importFile(String filename) throws ImportFileException {
    try {
      _network.importFile(filename);
    } catch (IOException | UnrecognizedEntryException /* FIXME maybe other exceptions */ e) {
      throw new ImportFileException(filename, e);
    }
  }
}



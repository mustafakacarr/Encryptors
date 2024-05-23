import axios from "axios";
import "./App.scss";
import React, { useState } from "react";

function App() {
  const [noteToEncrypt, setNoteToEncrypt] = useState("");
  const [noteIdToDecrpyt, setNoteIdToDecrpyt] = useState("");
  const [noteKeyToDecrypt, setNoteKeyToDecrypt] = useState("");
  const [keyFromServer, setKeyFromServer] = useState("");
  const [hashedNote, setHashedNote] = useState("");
  const [noteIdFromServer, setNoteIdFromServer] = useState("");
  const [decryptedNote, setDecryptedNote] = useState("");
  const [encryptionError, setEncryptionError] = useState("");
  const [decryptionError, setDecryptionError] = useState("");
  const handleDecryptNote = async () => {
    try {
      const response = await axios.get(`/api/notes/${noteIdToDecrpyt}`, {
        params: { key: noteKeyToDecrypt },
      });
      console.log("🚀 ~ handleDecryptNote ~ response:", response);

      setDecryptionError("");
      setDecryptedNote(response.data.decryptedMessage);
      console.log("Decrypting Note");
    } catch (error) {
      setDecryptionError(error.response.data.error);
      console.error(error);
    }
  };

  const handleEncryptNote = async () => {
    try {
      const response = await axios.post("/api/notes/create", null, {
        params: { message: noteToEncrypt },
      });
      console.log("🚀 ~ handleEncryptNote ~ response:", response);
      setKeyFromServer(response.data.key);
      setHashedNote(response.data.encryptedMessage);
      setNoteIdFromServer(response.data.noteId);
      setEncryptionError("");
      console.log("Decrypting Note");
    } catch (error) {

      setEncryptionError(error.response.data.error);
      console.error(error);
    }
  };

  return (
    <div className="container mt-5">
      <label className="fs-2 d-flex justify-content-center">
        Encrypt Your Note Securely
      </label>

      <div className="card shadow-sm mt-4">
        <div className="card-body">
          <div className="mb-3">
            <label htmlFor="message" className="form-label fs-5">
              Type your note message
            </label>
            <textarea
              id="message"
              className="form-control mb-3"
              onChange={(e) => setNoteToEncrypt(e.target.value)}
            ></textarea>
            <button className="btn btn-success" onClick={handleEncryptNote}>
              Encrypt Note
            </button>
          </div>
        </div>
        {encryptionError !== "" ? (
          <div className="alert alert-danger">{encryptionError}</div>
        ) : (
          keyFromServer &&
          hashedNote && (
            <div className="card-footer">
              <b>Response from Server</b> <br />
              Key : {keyFromServer}
            <br/>  <code className="text-muted">
                {" "}
                (You can check x-timestamp in response header)
              </code>
              <br />
              Hashed Note : {hashedNote}
              <br />
              Note Id : {noteIdFromServer}
            </div>
          )
        )}
      </div>
      <label className="fs-2 d-flex justify-content-center mt-4">
        {" "}
        Reach Old Notes Via Key and Id
      </label>

      <div className="card shadow-sm mt-4">
        <div className="card-body">
          <div className="mb-3">
            <label htmlFor="message" className="form-label fs-5">
              Type your note id
            </label>
            <input
              type="text"
              className="form-control mb-3"
              onChange={(e) => setNoteIdToDecrpyt(e.target.value)}
            />
            <label className="form-label fs-5">Type your key</label>
            <input
              type="text"
              className="form-control mb-3"
              onChange={(e) => setNoteKeyToDecrypt(e.target.value)}
            />
            <button className="btn btn-success" onClick={handleDecryptNote}>
              Decrypt Message
            </button>
          </div>
        </div>
        {decryptionError !== "" ? (
          <div className="alert alert-danger">{decryptionError}</div>
        ) : (
          <>
            {decryptedNote && (
              <div className="card-footer">
                <b>Decrypted Message:</b> {decryptedNote}
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
}

export default App;

package test.memory;

import domain.IceException;
import memory.FileLevelRepository;
import memory.LevelRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Validates failure paths for the file-based level repository.
 */
class LevelRepositoryTest {

    @TempDir
    Path tempDir;

    /**
     * Ensures that requesting a missing level file surfaces an IceException.
     */
    @Test
    void loadLevel_invalidFile_throwsIceException() {
        LevelRepository repository = new FileLevelRepository(tempDir);
        assertThrows(IceException.class, () -> repository.loadLevel(99));
    }
}

package BlogAPI.Service;

import BlogAPI.Entity.TestEntity;
import BlogAPI.Mapper.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private final TestRepository testRepository;

    @Autowired
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public void AddTestEntity(TestEntity testEntity) {
        testRepository.save(testEntity);
    }
}

package itu.matelas.demo.classement;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ClassementMachineService {

    private final ClassementMachineRepository classementMachineRepository;

    public ClassementMachineService(ClassementMachineRepository classementMachineRepository) {
        this.classementMachineRepository = classementMachineRepository;
    }

    public List<ClassementMachine> genererClassement() {
        return classementMachineRepository.findAll();
    }
}

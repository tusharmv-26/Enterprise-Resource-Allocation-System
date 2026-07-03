package com.incture.erasm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import com.incture.erasm.entity.Skill;
import com.incture.erasm.repository.SkillRepository;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {
    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillService skillService;

    private Skill skill;

    @BeforeEach
    void setUp() {
        skill = new Skill();

        skill.setId(1L);
        skill.setSkillName("Java");
        skill.setSkillCategory("Backend");
    }

    @Test
    void testSaveSkillSuccess() {
        when(skillRepository.save(skill)).thenReturn(skill);

        Skill saved = skillService.saveSkill(skill);

        assertNotNull(saved);
        assertEquals("Java", saved.getSkillName());
        assertEquals("Backend", saved.getSkillCategory());

        verify(skillRepository).save(skill);
    }

    @Test
    void testGetAllSkills() {
        when(skillRepository.findAll())
                .thenReturn(Arrays.asList(skill));

        assertEquals(1,
                skillService.getAllSkills().size());

        verify(skillRepository).findAll();
    }

    @Test
    void testGetAllSkillsEmpty() {
        when(skillRepository.findAll())
                .thenReturn(Collections.emptyList());

        assertTrue(skillService.getAllSkills().isEmpty());

        verify(skillRepository).findAll();
    }

    @Test
    void testGetSkillByIdSuccess() {
        when(skillRepository.findById(1L))
                .thenReturn(Optional.of(skill));

        Skill found = skillService.getSkillById(1L);

        assertNotNull(found);
        assertEquals("Java", found.getSkillName());

        verify(skillRepository).findById(1L);
    }

    @Test
    void testGetSkillByIdNotFound() {
        when(skillRepository.findById(5L))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> skillService.getSkillById(5L));

        verify(skillRepository).findById(5L);
    }

    @Test
    void testUpdateSkill() {
        skill.setSkillName("Spring Boot");

        when(skillRepository.save(skill))
                .thenReturn(skill);

        Skill updated = skillService.updateSkill(skill);

        assertEquals("Spring Boot",
                updated.getSkillName());

        verify(skillRepository).save(skill);
    }

    @Test
    void testDeleteSkill() {
        doNothing().when(skillRepository).deleteById(1L);

        skillService.deleteSkill(1L);

        verify(skillRepository).deleteById(1L);
    }

    @Test
    void testRepositoryInteractions() {
        when(skillRepository.findAll())
                .thenReturn(Collections.emptyList());

        skillService.getAllSkills();

        verify(skillRepository, times(1)).findAll();
        verifyNoMoreInteractions(skillRepository);
    }

    @Test
    void testSaveNullSkill() {
        when(skillRepository.save(null))
                .thenReturn(null);

        Skill saved = skillService.saveSkill(null);

        assertNull(saved);

        verify(skillRepository).save(null);
    }
}
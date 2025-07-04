package com.api.app.repositories;

import com.api.app.models.ImovelModel;
import com.api.app.models.MoradorModel;
import com.api.app.models.ProprietarioModel;
import com.api.app.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImovelRepository extends JpaRepository<ImovelModel, Long> {

    List<ImovelModel> findByMoradorId(Long moradorId);
    List<ImovelModel> findByMorador(MoradorModel morador);



}

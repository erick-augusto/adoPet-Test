package br.com.alura.adopet.api.validacoes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;

@ExtendWith(MockitoExtension.class)
public class ValidacaoPetComAdocaoEmAndamentoTest {

    @InjectMocks
    ValidacaoPetComAdocaoEmAndamento validacao;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocaoDePetComPedidoEmAndamento() {
        // ARRANGE
        BDDMockito.given(adocaoRepository.existsByPetIdAndStatus(dto.idPet(),StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);

        // ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }

    @Test
    void deveriaPermitirSolicitacaoDeAdocaoDePetComPedidoInexistente() {
        //Arrange
        given(adocaoRepository.existsByPetIdAndStatus(dto.idPet(),StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);

        //Act + Assert
        assertDoesNotThrow(()->validacao.validar(dto));
    }
}

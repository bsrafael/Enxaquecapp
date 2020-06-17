package com.enxaquecapp.app.ui.episode

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enxaquecapp.app.api.ApiCallback
import com.enxaquecapp.app.api.client.EpisodeClient
import com.enxaquecapp.app.api.client.OptionClient
import com.enxaquecapp.app.api.models.input.EpisodeInputModel
import com.enxaquecapp.app.api.models.input.EpisodePatchInputModel
import com.enxaquecapp.app.model.Cause
import com.enxaquecapp.app.model.Episode
import com.enxaquecapp.app.model.Location
import com.enxaquecapp.app.model.Relief
import java.util.*

class EpisodeViewModel: ViewModel() {

    var causes: MutableLiveData<List<Cause>> = MutableLiveData<List<Cause>>()
    var locations: MutableLiveData<List<Location>> = MutableLiveData<List<Location>>()
    var reliefs: MutableLiveData<List<Relief>> = MutableLiveData<List<Relief>>()
    var episode: MutableLiveData<Episode> = MutableLiveData<Episode>()
    var error: MutableLiveData<String> = MutableLiveData<String>()

    private fun mockCauses() {
        val c = listOf<Cause>(
            Cause(UUID.randomUUID(), "Stress"),
            Cause(UUID.randomUUID(), "Falta de sono"),
            Cause(UUID.randomUUID(), "Ansiedade"),
            Cause(UUID.randomUUID(), "Falta de água"),
            Cause(UUID.randomUUID(), "Café"),
            Cause(UUID.randomUUID(), "Bebida"),
            Cause(UUID.randomUUID(), "Barulho"),
            Cause(UUID.randomUUID(), "Luz"),
            Cause(UUID.randomUUID(), "Som"),
            Cause(UUID.randomUUID(), "Cheiro"),
            Cause(UUID.randomUUID(), "Menstruação"),
            Cause(UUID.randomUUID(), "Menopausa"),
            Cause(UUID.randomUUID(), "Mudança climática"),
            Cause(UUID.randomUUID(), "Viagem")
        )

        causes.postValue(c)
    }

    private fun mockLocations() {
        val l = listOf<Location>(
            Location(UUID.randomUUID(), "Em casa"),
            Location(UUID.randomUUID(), "Na rua"),
            Location(UUID.randomUUID(), "No trabalho"),
            Location(UUID.randomUUID(), "Em uma loja"),
            Location(UUID.randomUUID(), "Em um restaurante"),
            Location(UUID.randomUUID(), "Em um bar"),
            Location(UUID.randomUUID(), "Em um carro"),
            Location(UUID.randomUUID(), "Em um ônibus"),
            Location(UUID.randomUUID(), "Em um avião")
        )
        locations.postValue(l)
    }


    private fun mockReliefs() {
        val l = listOf<Relief>(
            Relief(UUID.randomUUID(), "Nada"),
            Relief(UUID.randomUUID(), "Dormi"),
            Relief(UUID.randomUUID(), "Bebi água"),
            Relief(UUID.randomUUID(), "Fiz um exercício"),
            Relief(UUID.randomUUID(), "Tomei um remédio"),
            Relief(UUID.randomUUID(), "Fiquei em um lugar escuro e quieto"),
            Relief(UUID.randomUUID(), "Pedi demissão")
        )
        reliefs.postValue(l)
    }

    fun loadOptions() {
        Log.i("EpisodeViewModel", "carregando opções")

        val client = OptionClient()

        client.getCauses(object: ApiCallback<List<Cause>> {

            override fun success(response: List<Cause>) {
                causes.postValue(response)
                Log.i("EpisodeViewModel", "causas carregadas com sucesso")
            }

            override fun failure(errorCode: Int, message: String) {
                Log.i("EpisodeViewModel", "falha ao carregar as causas ($errorCode) $message")
                error.postValue("Falha ao carregar as causas\n$message")
            }

            override fun error() {
                Log.e("EpisodeViewModel", "falha interna ao carregar as causas")
                error.postValue("Falha interna ao carregar as causas")
            }
        })

        client.getLocations(object: ApiCallback<List<Location>> {

            override fun success(response: List<Location>) {
                locations.postValue(response)
                Log.i("EpisodeViewModel", "locais carregados com sucesso")
            }

            override fun failure(errorCode: Int, message: String) {
                Log.i("EpisodeViewModel", "falha ao carregar os locais ($errorCode) $message")
                error.postValue("Falha ao carregar os locais\n$message")
            }

            override fun error() {
                Log.e("EpisodeViewModel", "falha interna ao carregar os locais")
                error.postValue("Falha interna ao carregar os locais")
            }
        })

        client.getReliefs(object: ApiCallback<List<Relief>> {

            override fun success(response: List<Relief>) {
                reliefs.postValue(response)
                Log.i("EpisodeViewModel", "alívios carregados com sucesso")
            }

            override fun failure(errorCode: Int, message: String) {
                Log.i("EpisodeViewModel", "falha ao carregar os alívios ($errorCode) $message")
                error.postValue("Falha ao carregar os alívios\n$message")
            }

            override fun error() {
                Log.e("EpisodeViewModel", "falha interna ao carregar os alívios")
                error.postValue("Falha interna ao carregar os alívios")
            }
        })
    }

    fun create(episode: Episode) {
        Log.i("EpisodeViewModel", "Criando: $episode")
        // TODO(Julio): Episode to Episode Input Model

        var im: EpisodeInputModel
//        create(im)
    }

    private fun create(im: EpisodeInputModel) {
        Log.i("EpisodeViewModel", "criando episódio")

        val client = EpisodeClient()

        client.create(im, object: ApiCallback<Episode> {

            override fun success(response: Episode) {
                episode.postValue(response)
                Log.i("EpisodeViewModel", "episódio criado com sucesso")
            }

            override fun failure(errorCode: Int, message: String) {
                Log.i("EpisodeViewModel", "falha ao criar o episódio ($errorCode) $message")
                error.postValue("Falha ao criar o episódio\n$message")
            }

            override fun error() {
                Log.e("EpisodeViewModel", "falha interna na criação do episódio")
                error.postValue("Falha interna ao criar o episódio")
            }
        })
    }

    fun update(id: UUID, im: EpisodePatchInputModel) {
        Log.i("EpisodeViewModel", "atualizando episódio")

        val client = EpisodeClient()

        client.update(id, im, object: ApiCallback<Episode> {

            override fun success(response: Episode) {
                episode.postValue(response)
                Log.i("EpisodeViewModel", "episódio atualizado com sucesso")
            }

            override fun failure(errorCode: Int, message: String) {
                Log.i("EpisodeViewModel", "falha ao atualizar o episódio ($errorCode) $message")
                // TODO(Rafael): error handling
            }

            override fun error() {
                Log.e("UserRepository", "falha interna na criação do episódio")
                // TODO(Rafael): error handling
            }
        })
    }

    fun remove(id: UUID) {
        Log.i("EpisodeViewModel", "removendo episódio")

        val client = EpisodeClient()

        client.delete(id, object: ApiCallback<Void> {

            override fun success(response: Void) {
                episode.postValue(null)
                Log.i("EpisodeViewModel", "episódio removido com sucesso")
            }

            override fun failure(errorCode: Int, message: String) {
                Log.i("EpisodeViewModel", "falha ao remover o episódio ($errorCode) $message")
                // TODO(Rafael): error handling
            }

            override fun error() {
                Log.e("UserRepository", "falha interna na remoção do episódio")
                // TODO(Rafael): error handling
            }
        })
    }
}
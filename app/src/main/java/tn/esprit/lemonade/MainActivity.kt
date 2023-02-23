package tn.esprit.lemonade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    /**
     * NE MODIFIEZ AUCUN NOM DE VARIABLE OU DE VALEUR OU LEURS VALEURS INITIALES.
     *
     * Tout ce qui est étiqueté var au lieu de val devrait être modifié dans les fonctions mais NE PAS
     * modifier leurs valeurs initiales déclarées ici, cela pourrait empêcher l'application de fonctionner correctement.
     */
    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"
    // SELECT représente l'état "cueillir du citron"
    private val SELECT = "select"
    // SQUEEZE représente l'état "presser le citron"
    private val SQUEEZE = "squeeze"
    // DRINK représente l'état "boire de la limonade"
    private val DRINK = "drink"
    // RESTART représente l'état où la limonade a été bue et le verre est vide
    private val RESTART = "restart"
    // Par défaut l'état est à select
    private var lemonadeState = "select"
    // Taille de citron par défaut est à -1
    private var lemonSize = -1
    // Par défaut le squeezeCount est à -1
    private var squeezeCount = -1

    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // === NE PAS MODIFIER LE CODE DANS LA DÉCLARATION IF SUIVANTE ===
        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }
        // === FIN IF ===

        lemonImage = findViewById(R.id.image_lemon_state)
        setViewElements()

        lemonImage!!.setOnClickListener {
            // TODO 1: appeler la méthode qui gère l'état lorsque l'image est cliquée
            clickLemonImage()
        }

        lemonImage!!.setOnLongClickListener {
            // TODO 2: remplacer 'false' par un appel à la fonction qui affiche le nombre de compressions
            showSnackbar()
        }
    }

    /**
     * === NE PAS MODIFIER CETTE MÉTHODE ===
     *
     * Cette méthode enregistre l'état de l'application si elle est mise en arrière-plan.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }

    /**
     * En cliquant, vous obtiendrez une réponse différente selon l'état.
     * Cette méthode détermine l'état et procède à l'action correcte.
     */
    private fun clickLemonImage() {
        // TODO 3: utilisez une instruction conditionnelle comme 'if' ou 'when'  pour suivre le limonadeState
        //  lorsque l'image est cliquée, nous devrons peut-être changer d'état à l'étape suivante dans la
        //  progression de la fabrication de limonade (ou au moins apporter quelques modifications à l'état actuel dans le
        //  cas de presser le citron). Cela devrait être fait dans cette instruction conditionnelle
        when (lemonadeState) {
            SELECT -> {
                lemonadeState = SQUEEZE
                lemonSize = LemonTree().pick()
                squeezeCount = 0
            }
            SQUEEZE -> {
                squeezeCount++
                lemonSize--
                if(lemonSize == 0) {
                    lemonadeState = DRINK
                    lemonSize = -1
                }
            }
            DRINK -> {
                lemonadeState = RESTART
            }
            RESTART -> {
                lemonadeState = SELECT
            }
        }
        setViewElements()

        // TODO 4: Lorsque l'on clique sur l'image dans l'état SELECT, l'état doit devenir SQUEEZE
        //  - La variable lemonSize doit être définie à l'aide de la méthode 'pick()' dans la classe LemonTree
        //  - Le squeezeCount doit être égal à 0 car nous n'avons pas encore pressé de citron.

        // TODO 5: Lorsque l'on clique sur l'image dans l'état SQUEEZE, le squeezeCount doit être
        //  AUGMENTÉ de 1 et la taille du citron doit être DIMINUÉE de 1.
        //  - Si le lemonSize a atteint 0, il a été pressé et l'état devrait devenir DRINK
        //  - De plus, lemonSize n'est plus pertinent et doit être défini sur -1

        // TODO 6: Lorsque l'on clique sur l'image dans l'état DRINK, l'état doit devenir RESTART

        // TODO 7: Lorsque l'on clique sur l'image dans l'état RESTART, l'état doit devenir SELECT

        // TODO 8: Enfin, avant que la fonction ne se termine, nous devons définir les éléments de vue de sorte que
        //  L'interface utilisateur peut refléter l'état correct

    }

    /**
     * Mettre en place les éléments d'affichage selon l'état.
     */
    private fun setViewElements() {
        val textAction: TextView = findViewById(R.id.text_action)
        // TODO 9: mettre en place une condition qui suit la limonadeState

        // TODO 10: pour chaque état, le textAction TextView doit être défini sur la chaîne correspondante
        //  du fichier de string ressources . Les strings sont nommées pour correspondre à l'état

        // TODO 11: De plus, pour chaque état, le lemonImage doit être défini sur le correspondant
        //  drawable des drawable ressources . Les drawables ont les mêmes noms que les strings
        //  mais rappelez-vous que ce sont des drawables, pas des chaînes.
        val stateImage = when(lemonadeState) {
            SELECT -> R.drawable.lemon_tree
            SQUEEZE -> R.drawable.lemon_squeeze
            DRINK -> R.drawable.lemon_drink
            else -> R.drawable.lemon_restart
        }
        val stateText = when(lemonadeState) {
            SELECT -> "A5tar kaaba 9ares"
            SQUEEZE -> "3asra behya belehi"
            DRINK -> "Gar3out wehed YALLA !"
            else -> "Bnin ? enzel w zid"
        }
        textAction.setText(stateText)
        lemonImage?.setImageResource(stateImage)
    }

    /**
     * === NE PAS MODIFIER CETTE MÉTHODE ===
     *
     * Un clic long sur l'image du citron montrera combien de fois le citron a été pressé.
     */
    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(
            findViewById(R.id.constraint_Layout),
            squeezeText,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}

/**
 * Une classe Lemon Tree avec une méthode "pick" pour cueillir un citron. La "taille" du citron est aléatoire
 * et détermine combien de fois un citron doit être pressé avant d'obtenir de la limonade.
 */
class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}

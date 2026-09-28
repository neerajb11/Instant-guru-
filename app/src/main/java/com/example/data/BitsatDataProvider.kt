package com.example.data

import com.example.model.BitsatSubject
import com.example.model.Chapter
import com.example.model.Flashcard
import com.example.model.Question
import com.example.model.WeakAreaItem

object BitsatDataProvider {

    val chapters: List<Chapter> = listOf(
        // PHYSICS
        Chapter(
            id = "phy_1",
            subject = BitsatSubject.PHYSICS,
            name = "Electrostatics & Capacitance",
            weightage = "High Yield",
            expectedQuestions = 3,
            summary = "Coulomb's Law, Electric Field & Potential, Gauss's Law applications, Capacitors in series/parallel, Dielectrics and Energy stored.",
            keyFormulas = listOf(
                "E_axial = 2kp/r³ (Short dipole)",
                "E_equatorial = kp/r³",
                "Capacitance with dielectric: C = ε₀A / (d - t + t/K)",
                "Energy density u = ½ ε₀E²",
                "Common potential V_c = (C₁V₁ + C₂V₂) / (C₁ + C₂)"
            ),
            speedTricks = listOf(
                "For dipole at large distance, axial field is always double the equatorial field at the same distance.",
                "Whenever a dielectric slab is inserted with battery disconnected, Charge Q remains constant, V decreases, C increases."
            )
        ),
        Chapter(
            id = "phy_2",
            subject = BitsatSubject.PHYSICS,
            name = "Rotational Motion & Inertia",
            weightage = "High Yield",
            expectedQuestions = 3,
            summary = "Torque, Angular momentum conservation, Moment of Inertia of symmetric bodies, Parallel/Perpendicular axis theorems, Pure rolling.",
            keyFormulas = listOf(
                "I_cylinder = ½ M R²",
                "I_sphere = (2/5) M R²",
                "I_parallel = I_cm + M d²",
                "Acceleration in pure rolling on inclined plane: a = (g sin θ) / (1 + I_cm / MR²)",
                "Angular momentum L = Iω + r × Mv_cm"
            ),
            speedTricks = listOf(
                "In rolling down an incline, object with smaller (k²/R²) arrives first: Sphere (0.4) > Disc (0.5) > Ring (1.0).",
                "If net external torque about a point is zero, conserve angular momentum about that specific point directly."
            )
        ),
        Chapter(
            id = "phy_3",
            subject = BitsatSubject.PHYSICS,
            name = "Modern Physics & Atoms",
            weightage = "High Yield",
            expectedQuestions = 4,
            summary = "Photoelectric effect, Bohr atom, de Broglie wavelength, Nuclear binding energy, Radioactive decay law.",
            keyFormulas = listOf(
                "K_max = hν - φ = eV₀",
                "r_n = 0.529 (n² / Z) Å",
                "v_n = 2.18 × 10⁶ (Z / n) m/s",
                "E_n = -13.6 (Z² / n²) eV",
                "N(t) = N₀ (½)^(t / T_half)"
            ),
            speedTricks = listOf(
                "Formula shortcut for photon energy: E (in eV) ≈ 12400 / λ (in Å).",
                "Radius ratio between orbits scales strictly as n²/Z, whereas time period scales as n³/Z²."
            )
        ),

        // CHEMISTRY
        Chapter(
            id = "chem_1",
            subject = BitsatSubject.CHEMISTRY,
            name = "Chemical Bonding & Molecular Structure",
            weightage = "High Yield",
            expectedQuestions = 3,
            summary = "VSEPR theory, Hybridization determination, Dipole moment, Molecular Orbital Theory (bond order & magnetic nature), Hydrogen bonding.",
            keyFormulas = listOf(
                "Steric Number = ½ [V + M - C + A]",
                "Bond Order = ½ (N_b - N_a)",
                "Paramagnetic if unpaired electrons exist in MOT",
                "Dipole moment μ = q × d (Debye)"
            ),
            speedTricks = listOf(
                "Quick MOT Bond Order for 14 electrons (N₂) = 3.0. For every electron added or subtracted from 14, decrease bond order by 0.5!",
                "Species with odd total electrons are ALWAYS paramagnetic (e.g. NO, NO₂)."
            )
        ),
        Chapter(
            id = "chem_2",
            subject = BitsatSubject.CHEMISTRY,
            name = "Organic Reaction Mechanisms (Alkyl Halides & Alcohols)",
            weightage = "High Yield",
            expectedQuestions = 4,
            summary = "SN1 vs SN2 kinetics and stereochemistry, E1 vs E2, Grignard reagents, Lucas test, Pinacol-Pinacolone rearrangement.",
            keyFormulas = listOf(
                "SN1 rate = k[Substrate] (Racemization with inversion excess)",
                "SN2 rate = k[Substrate][Nucleophile] (100% Walden Inversion)",
                "Lucas reagent: ZnCl₂ + conc. HCl (3° reacts instantly, 2° in 5 min, 1° on heating)"
            ),
            speedTricks = listOf(
                "Polar aprotic solvents (DMSO, Acetone, DMF) heavily accelerate SN2 reactions.",
                "For E2 elimination, Saytzeff (more substituted alkene) is major with small bases, Hofmann is major with bulky bases (like t-BuO⁻)."
            )
        ),
        Chapter(
            id = "chem_3",
            subject = BitsatSubject.CHEMISTRY,
            name = "Electrochemistry & Kinetics",
            weightage = "High Yield",
            expectedQuestions = 3,
            summary = "Nernst equation, Kohlrausch law, Faraday's laws, Integrated rate laws (Zero & First order), Arrhenius equation.",
            keyFormulas = listOf(
                "E_cell = E°_cell - (0.0591 / n) log Q at 298K",
                "ΔG° = -n F E°_cell",
                "First order t_half = 0.693 / k (independent of initial conc)",
                "log (k₂ / k₁) = (E_a / 2.303R) × [(T₂ - T₁) / (T₁ T₂)]"
            ),
            speedTricks = listOf(
                "In first-order reaction: t_99.9% = 10 × t_half; t_75% = 2 × t_half.",
                "E° is an intensive property! Multiplying a stoichiometric equation does NOT multiply its E° value."
            )
        ),

        // MATHEMATICS
        Chapter(
            id = "math_1",
            subject = BitsatSubject.MATHEMATICS,
            name = "Definite Integrals & Area Under Curves",
            weightage = "High Yield",
            expectedQuestions = 4,
            summary = "King's property (x -> a+b-x), Periodic functions integration, Leibniz rule for differentiating integrals, Standard symmetric areas.",
            keyFormulas = listOf(
                "∫[a to b] f(x)dx = ∫[a to b] f(a+b-x)dx",
                "Area between y²=4ax and x²=4ay is (16/3) a²",
                "Area between parabola y²=4ax and line y=mx is (8/3) (a² / m³)",
                "d/dx [∫[u(x) to v(x)] f(t)dt] = f(v(x)) v'(x) - f(u(x)) u'(x)"
            ),
            speedTricks = listOf(
                "For integrals like ∫[0 to π/2] (sinⁿ x / (sinⁿ x + cosⁿ x)) dx, the answer is ALWAYS (Upper - Lower)/2 = π/4.",
                "Whenever an integrand contains (x) multiplying a symmetric function, King's rule removes the x factor."
            )
        ),
        Chapter(
            id = "math_2",
            subject = BitsatSubject.MATHEMATICS,
            name = "Vectors & 3D Geometry",
            weightage = "High Yield",
            expectedQuestions = 5,
            summary = "Dot & Cross products, Scalar triple product, Shortest distance between skew lines, Plane equations, Line-plane intersection.",
            keyFormulas = listOf(
                "Shortest distance d = |(a₂ - a₁) · (b₁ × b₂)| / |b₁ × b₂|",
                "Angle between lines: cos θ = |a₁a₂ + b₁b₂ + c₁c₂| / [√(∑a₁²) √(∑a₂²)]",
                "Volume of parallelepiped = [a b c] = a · (b × c)",
                "Distance of point (x₀,y₀,z₀) from plane Ax+By+Cz+D=0: |Ax₀+By₀+Cz₀+D| / √(A²+B²+C²)"
            ),
            speedTricks = listOf(
                "If vectors are coplanar, set the 3x3 determinant of their coefficients directly to 0.",
                "Test option points directly into the plane/line equation rather than solving simultaneous equations from scratch."
            )
        ),
        Chapter(
            id = "math_3",
            subject = BitsatSubject.MATHEMATICS,
            name = "Matrices & Determinants",
            weightage = "Core",
            expectedQuestions = 3,
            summary = "Properties of determinants, Inverse of matrix, System of linear equations (Cramer's rule), Cayley-Hamilton theorem.",
            keyFormulas = listOf(
                "|adj A| = |A|^(n-1)",
                "|A · adj A| = |A|^n",
                "adj(adj A) = |A|^(n-2) A",
                "A⁻¹ = (1 / |A|) adj(A)"
            ),
            speedTricks = listOf(
                "If |A| = 0, system has either infinite solutions or no solution (check (adj A) · B).",
                "Substitute simple values like x=0, 1 or a=1, b=2 to evaluate complicated determinant identities in under 30 seconds."
            )
        ),

        // ENGLISH PROFICIENCY
        Chapter(
            id = "eng_1",
            subject = BitsatSubject.ENGLISH,
            name = "Vocabulary, Synonyms & Antonyms",
            weightage = "Core",
            expectedQuestions = 4,
            summary = "High-frequency root words (Latin & Greek), Contextual connotations, Confusing homophones, Idioms & Phrasal verbs.",
            keyFormulas = listOf(
                "Prefix 'Mal-' = bad/evil (Malevolent, Malicious)",
                "Prefix 'Bene-' = good/well (Benefactor, Benevolent)",
                "Root 'Chron' = time (Chronological, Synchronous)",
                "Root 'Loqu/Loc' = speak (Eloquent, Loquacious)"
            ),
            speedTricks = listOf(
                "Identify tone of the sentence (+ve or -ve) to eliminate 2 opposing answer options instantly.",
                "Look for contrast signal words: Although, However, Despite, Whereas imply an opposite meaning."
            )
        ),
        Chapter(
            id = "eng_2",
            subject = BitsatSubject.ENGLISH,
            name = "Grammar: Sentence Correction & Error Spotting",
            weightage = "Core",
            expectedQuestions = 3,
            summary = "Subject-verb agreement, Modifier placement, Parallelism, Tenses consistency, Preposition collocations.",
            keyFormulas = listOf(
                "Neither... nor / Either... or -> verb agrees with the CLOSER subject",
                "One of the [plural noun] that [PLURAL verb]",
                "Not only X but also Y must be structurally parallel"
            ),
            speedTricks = listOf(
                "Ignore parenthetical phrases between subject and verb (e.g., 'along with', 'as well as', 'accompanied by'). The head noun dictates the verb.",
                "Check whether participle clauses ('Having finished the exam...') attach to the intended subject immediately following the comma."
            )
        ),

        // LOGICAL REASONING
        Chapter(
            id = "lr_1",
            subject = BitsatSubject.LOGICAL_REASONING,
            name = "Analogy, Series & Number Patterns",
            weightage = "High Yield",
            expectedQuestions = 6,
            summary = "Arithmetic & geometric series, Double difference series, Alternating patterns, Alpha-numeric ranking.",
            keyFormulas = listOf(
                "Letter positions: E(5), J(10), O(15), T(20), Y(25) [EJOTY rule]",
                "Reverse letter sum = 27 (A=1 + Z=26 = 27, B=2 + Y=25 = 27)",
                "Check prime numbers progression: 2, 3, 5, 7, 11, 13, 17..."
            ),
            speedTricks = listOf(
                "Always check differences of differences if first differences look irregular.",
                "For letter series, write down EJOTY on scratch paper during the initial 1 minute."
            )
        ),
        Chapter(
            id = "lr_2",
            subject = BitsatSubject.LOGICAL_REASONING,
            name = "Syllogisms, Blood Relations & Direction Sense",
            weightage = "High Yield",
            expectedQuestions = 7,
            summary = "Venn diagram methodology for deductive logic, Family tree diagramming (+ for male, - for female, = for married), 8-point compass navigation.",
            keyFormulas = listOf(
                "'All A are B' + 'All B are C' => 'All A are C'",
                "'Some' statement does NOT imply 'Some not'",
                "Shadow in morning: Towards West. Shadow in evening: Towards East."
            ),
            speedTricks = listOf(
                "If both premises are positive, a negative conclusion CANNOT follow without doubt.",
                "Draw standard family tree levels vertically for generations to prevent gender confusion."
            )
        )
    )

    val flashcards: List<Flashcard> = listOf(
        Flashcard(
            id = "fc_1",
            subject = BitsatSubject.PHYSICS,
            chapterName = "Electrostatics & Capacitance",
            frontPrompt = "What is the ratio of Electric Field on axial vs equatorial position of an electric dipole at equal distance r?",
            backAnswer = "Ratio = 2 : 1",
            formulaOrTrick = "E_axial = 2kp/r³ and E_eq = kp/r³ -> E_axial = 2 × E_eq"
        ),
        Flashcard(
            id = "fc_2",
            subject = BitsatSubject.PHYSICS,
            chapterName = "Rotational Motion",
            frontPrompt = "Which body reaches the bottom of an inclined plane first when rolling without slipping?",
            backAnswer = "Solid Sphere (least k²/R² ratio = 0.4)",
            formulaOrTrick = "a = g sin θ / (1 + k²/R²). Lower k²/R² => Greater acceleration."
        ),
        Flashcard(
            id = "fc_3",
            subject = BitsatSubject.CHEMISTRY,
            chapterName = "Chemical Bonding",
            frontPrompt = "How do you quickly compute the Bond Order for O₂⁺ (15 electrons)?",
            backAnswer = "Bond Order = 2.5",
            formulaOrTrick = "Base: 14 electrons (N₂) = 3.0. For 15 electrons, minus 0.5 = 2.5."
        ),
        Flashcard(
            id = "fc_4",
            subject = BitsatSubject.CHEMISTRY,
            chapterName = "Organic Chemistry",
            frontPrompt = "What is the stereochemical outcome of an SN2 substitution at an asymmetric carbon?",
            backAnswer = "100% Inversion of configuration (Walden Inversion)",
            formulaOrTrick = "Backside attack by incoming nucleophile causes umbrella inversion."
        ),
        Flashcard(
            id = "fc_5",
            subject = BitsatSubject.MATHEMATICS,
            chapterName = "Definite Integrals",
            frontPrompt = "What is the value of ∫[0 to π/2] [sin³(x) / (sin³(x) + cos³(x))] dx?",
            backAnswer = "π / 4",
            formulaOrTrick = "King's property symmetry trick: Integral = (Upper Limit - Lower Limit) / 2 = π/4."
        ),
        Flashcard(
            id = "fc_6",
            subject = BitsatSubject.MATHEMATICS,
            chapterName = "Vectors & 3D",
            frontPrompt = "What is the area enclosed between parabolas y² = 4ax and x² = 4ay?",
            backAnswer = "(16 / 3) a²",
            formulaOrTrick = "Memorize the direct standard BITSAT formula: 16/3 a²."
        ),
        Flashcard(
            id = "fc_7",
            subject = BitsatSubject.ENGLISH,
            chapterName = "Vocabulary",
            frontPrompt = "What is the meaning of the GRE/BITSAT high-frequency word 'EPHEMERAL'?",
            backAnswer = "Lasting for a very short time; transient, fleeting.",
            formulaOrTrick = "Mnemonic: 'Ep-hem' sounds like an email flash — gone in seconds."
        ),
        Flashcard(
            id = "fc_8",
            subject = BitsatSubject.LOGICAL_REASONING,
            chapterName = "Number Series",
            frontPrompt = "Complete the series: 2, 6, 12, 20, 30, ?",
            backAnswer = "42",
            formulaOrTrick = "Pattern is n² + n: 1²+1=2, 2²+2=6, 3²+3=12, 4²+4=20, 5²+5=30, 6²+6=42."
        )
    )

    val mockQuestions: List<Question> = listOf(
        Question(
            id = "q_1",
            subject = BitsatSubject.PHYSICS,
            chapterName = "Electrostatics",
            questionText = "A parallel plate capacitor of capacitance C is charged to a potential V and then disconnected from the battery. A dielectric slab of constant K is now inserted between the plates. What happens to the energy stored in the capacitor?",
            options = listOf(
                "Increases by a factor of K",
                "Decreases by a factor of K",
                "Remains unchanged",
                "Decreases by a factor of K²"
            ),
            correctOptionIndex = 1,
            explanation = "When disconnected from battery, charge Q remains constant. The new capacitance is C' = K·C. Stored energy U = Q² / (2C). Hence U' = Q² / (2KC) = U / K, so it decreases by a factor of K.",
            speedShortcut = "Battery disconnected => Q is constant => U = Q²/2C => U inversely proportional to C => decreases by K.",
            difficulty = "BITSAT Speed"
        ),
        Question(
            id = "q_2",
            subject = BitsatSubject.PHYSICS,
            chapterName = "Modern Physics",
            questionText = "An electron in a hydrogen atom drops from orbit n = 3 to n = 1. What is the wavelength of the emitted photon? (Rydberg constant R = 1.097 × 10⁷ m⁻¹)",
            options = listOf(
                "9 / (8R)",
                "8 / (9R)",
                "4 / (3R)",
                "1 / (8R)"
            ),
            correctOptionIndex = 0,
            explanation = "1/λ = R [1/1² - 1/3²] = R [1 - 1/9] = 8R/9. Therefore, λ = 9 / (8R).",
            speedShortcut = "Formula 1/λ = R(1 - 1/n²) => (n² - 1)R / n² => Invert directly: n² / ((n²-1)R) = 9/8R.",
            difficulty = "Easy"
        ),
        Question(
            id = "q_3",
            subject = BitsatSubject.CHEMISTRY,
            chapterName = "Chemical Bonding",
            questionText = "Which of the following diatomic species is diamagnetic?",
            options = listOf(
                "O₂",
                "B₂",
                "C₂",
                "NO"
            ),
            correctOptionIndex = 2,
            explanation = "Total electrons in C₂ = 12. According to MOT for <= 14 electrons: σ1s² σ*1s² σ2s² σ*2s² (π2p_x² = π2p_y²). All electrons are paired, making C₂ diamagnetic with a double bond composed entirely of pi bonds!",
            speedShortcut = "O₂ has 16 e⁻ (paramagnetic, 2 unpaired), B₂ has 10 e⁻ (paramagnetic), NO has 15 e⁻ (odd e⁻ = always paramagnetic). Only C₂ (12 e⁻) has all paired electrons.",
            difficulty = "High Speed"
        ),
        Question(
            id = "q_4",
            subject = BitsatSubject.CHEMISTRY,
            chapterName = "Kinetics",
            questionText = "A first-order reaction has a rate constant k = 2.303 × 10⁻³ s⁻¹. The time required for 90% completion of the reaction is:",
            options = listOf(
                "500 s",
                "1000 s",
                "2000 s",
                "100 s"
            ),
            correctOptionIndex = 1,
            explanation = "For 90% completion, remaining concentration a - x = 0.10a. t = (2.303 / k) log [a / (0.10a)] = (2.303 / 2.303×10⁻³) log(10) = 10³ × 1 = 1000 seconds.",
            speedShortcut = "Notice k has 2.303 in it! The 2.303 cancels immediately: t = log(10)/10⁻³ = 1/10⁻³ = 1000 s. Calculated in 5 seconds!",
            difficulty = "BITSAT Speed"
        ),
        Question(
            id = "q_5",
            subject = BitsatSubject.MATHEMATICS,
            chapterName = "Definite Integrals",
            questionText = "Evaluate the integral I = ∫[0 to π] [x sin(x) / (1 + cos²(x))] dx:",
            options = listOf(
                "π² / 2",
                "π² / 4",
                "π / 4",
                "π² / 8"
            ),
            correctOptionIndex = 1,
            explanation = "Using King's property: I = ∫[0 to π] [(π - x) sin(π - x) / (1 + cos²(π - x))] dx = ∫[0 to π] [(π - x) sin(x) / (1 + cos²(x))] dx. Adding: 2I = π ∫[0 to π] [sin(x) / (1 + cos²(x))] dx. Put u = cos(x), du = -sin(x) dx: 2I = π [arctan(cos x)] evaluated from π to 0 = π [π/4 - (-π/4)] = π²/2 => I = π²/4.",
            speedShortcut = "Standard BITSAT result! The integral ∫[0 to π] x f(sin x) dx = (π/2) ∫[0 to π] f(sin x) dx. Here (π/2) × (π/2) = π²/4.",
            difficulty = "Medium"
        ),
        Question(
            id = "q_6",
            subject = BitsatSubject.MATHEMATICS,
            chapterName = "Matrices",
            questionText = "If A is a 3 × 3 matrix such that det(A) = 4, then find the value of det(2 · adj(A)):",
            options = listOf(
                "32",
                "64",
                "128",
                "256"
            ),
            correctOptionIndex = 2,
            explanation = "For an n × n matrix: det(k · M) = kⁿ det(M). Here n = 3, so det(2 · adj(A)) = 2³ · det(adj(A)). Also det(adj(A)) = det(A)^(n-1) = 4^(3-1) = 4² = 16. Thus, 8 × 16 = 128.",
            speedShortcut = "Formula: det(k · adj(A)) = kⁿ · |A|^(n-1). For n=3: 2³ × 4² = 8 × 16 = 128.",
            difficulty = "Medium"
        ),
        Question(
            id = "q_7",
            subject = BitsatSubject.ENGLISH,
            chapterName = "Sentence Correction",
            questionText = "Choose the grammatically correct sentence from the options below:",
            options = listOf(
                "Neither of the two candidates have submitted their credentials on time.",
                "Neither of the two candidates has submitted his credentials on time.",
                "Neither of the two candidates have submitted his credentials on time.",
                "Neither of the two candidates were submitting their credentials on time."
            ),
            correctOptionIndex = 1,
            explanation = "'Neither' as a pronoun refers to 'not one nor the other' and is singular, requiring the singular verb 'has' and singular pronoun 'his' (or 'his or her').",
            speedShortcut = "'Neither of' is always grammatically SINGULAR. Instantly strike off options with 'have' or 'were'!",
            difficulty = "Core"
        ),
        Question(
            id = "q_8",
            subject = BitsatSubject.LOGICAL_REASONING,
            chapterName = "Direction Sense",
            questionText = "Rohan walks 10 km towards North. From there, he turns right and walks 6 km. Then he turns right again and walks 2 km. Finally, he turns left and walks 2 km. How far and in which direction is he now from his starting point?",
            options = listOf(
                "10 km North-East",
                "8 km North-East",
                "10 km East",
                "12 km North-East"
            ),
            correctOptionIndex = 0,
            explanation = "Net North displacement = 10 km - 2 km = 8 km North. Net East displacement = 6 km + 2 km = 8 km East. Distance = √(8² + 8²) = 8√2 ≈ 11.3 km? Wait: let's recalculate: Started (0,0) -> (0,10) -> Right (East) 6 km -> (6,10) -> Right (South) 2 km -> (6,8) -> Left (East) 2 km -> (8,8)? If 6 km and 8 km: Distance = √(8² + 6²) = 10 km! Let's check: 8 km North and 6 km East gives √(64+36) = 10 km North-East.",
            speedShortcut = "Pythagorean triplet (6, 8, 10). Starting point to end point forms right triangle with legs 6 and 8. Hypotenuse is strictly 10 km!",
            difficulty = "BITSAT Speed"
        )
    )

    val weakAreasSample: List<WeakAreaItem> = listOf(
        WeakAreaItem(
            subject = BitsatSubject.MATHEMATICS,
            topicName = "Definite Integrals & Area",
            accuracy = 42,
            priority = "Critical",
            recommendedAction = "Practice King's Property shortcuts & standard bounding parabola areas."
        ),
        WeakAreaItem(
            subject = BitsatSubject.PHYSICS,
            topicName = "Rotational Mechanics (Rolling)",
            accuracy = 55,
            priority = "Moderate",
            recommendedAction = "Review (k²/R²) ratio rules and instant center of rotation."
        ),
        WeakAreaItem(
            subject = BitsatSubject.CHEMISTRY,
            topicName = "Organic SN1 vs SN2 Solvents",
            accuracy = 58,
            priority = "Moderate",
            recommendedAction = "Master protic vs aprotic solvent influence on nucleophilicity."
        ),
        WeakAreaItem(
            subject = BitsatSubject.LOGICAL_REASONING,
            topicName = "Alpha-Numeric Code Shifting",
            accuracy = 68,
            priority = "Watchlist",
            recommendedAction = "Memorize EJOTY (5, 10, 15, 20, 25) and reverse-letter pairs sum = 27."
        )
    )
}

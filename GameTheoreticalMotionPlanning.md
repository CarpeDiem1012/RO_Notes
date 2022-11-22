<link rel="stylesheet" type="text/css" href="auto-number-title.css" />

- [Game Theory Intro Concepts](#game-theory-intro-concepts)
  - [Cooperative (not discussed)](#cooperative-not-discussed)
  - [Non-cooperative (fall within the scope)](#non-cooperative-fall-within-the-scope)
- [Game Theory Concepts](#game-theory-concepts)
  - [General Features](#general-features)
    - [Necessity](#necessity)
    - [Scenerios](#scenerios)
    - [Decision sequence](#decision-sequence)
    - [Solution Concept](#solution-concept)
    - [Information completeness](#information-completeness)
    - [Bounded rationality](#bounded-rationality)
    - [Differential games](#differential-games)
- [Game Models of Dynamic Interaction](#game-models-of-dynamic-interaction)
  - [Basis](#basis)
  - [Best Responses and Nash Equilibria](#best-responses-and-nash-equilibria)
  - [E](#e)

# Game Theory Intro Concepts
- Each player chooses from a set of available actions (or  *alternatives*) in a bid to maximize her payoff (*utility* or *reward*) or to minimize cost (or *loss*).
- Crucially, the payoff also depends on the other player's actions
- Rational --> to consider how the others would act

## Cooperative (not discussed)

## Non-cooperative (fall within the scope)

# Game Theory Concepts

## General Features
> Context need to be precisely identified.
- Necessity
- Scenerios
- Decision sequence
- Information available

### Necessity
- Deal with new source of uncertainty about environment, others' decisions and intentions
- Classic planner --> simple and unilateral modeling of others' behaviours --> over-conservative, passive behaviour based
- Game theoritical planner --> consider the effects of my actions to others' behaviours --> second-order prediction

### Scenerios
- Zero-sum --> car racing
  - One player's payoff is equivelent to another's loss 
- Genral-sum --> urban driving
  - Personal objectives(where to go, style preference)
  - Coupled objectives(collision avoidance, safety margin)
  - Not sum to zero

### Decision sequence
- sequential game
  - **Defination:** before making decision, have chances to observe others' actions 
  - **Example:** playing chess, poker --> inheretn asymmetry
  - **Feature:** possess a "simple" leader-follower structure --> Stackelberg game
- simultaneous game
  - each player make decisions independently
> Strictly and ideally speaking, most of the scenarios are sequential games, . Whearas, due to perception latency, agents have no spare time to react to infomative observations. Therefore, many scenarios are actually simultaneous game when the decisions have to be made before useful observations are processed.

### Solution Concept

> Proved by John Nash that, every game with finite actions will have at least one equilibrium --> what if we have multiple?

- Equilibria selection
  - Impose more criteria as restrictions (stablity, cost...) --> social optima, vehicles subordinate to humans
    - same game, more criterion
  - Change the rules to make the game well-posed with clear and unique solution (mechanism re-design) --> introduce traffic light
    - different game, same criterion

- Security Strategy: guarantee at least certain payoff

### Information completeness
- Complete information
  - Under a strong assumption which might not always hold true: the decision making model, namely the cost function, of each player is known to every agent.
- Incomplete information
  - how to learn the cost functions of other agents to shrink the uncertainty?
    - inverse optimal control == inverse reinforcement learning
  - how to take actions with uncertain decison models?
    - The essence of uncertain decision model is not knowing that kind of game we are playing
    - Solution: Bayesian game remapping --> introduce a extra player called "Nature" who acts sequentially prior to all other agents --> make the problem tractable

### Bounded rationality
To yield an seemingly suboptimal, but good enough solution under limited resoure of sensing, computations. --> approximated, suboptimal, anytime algorithms

### Differential games
- The dynamics of many robotic systems with multiple agents should be naturally described in a time-continuous ODE, but not in many discrete stages
- The game strategy algorithm should be set in align with the transition of the system state, which have to be executed as iterations
- Furthurmore, the hybrid system (continuous-time + discrete-time + event-based)
# Game Models of Dynamic Interaction

## Basis

## Best Responses and Nash Equilibria

## E
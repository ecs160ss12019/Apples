# Acceptance Tests

### First Sprint
|User Story|Acceptance Test|Critical|Test Result|Comments|
|---|---|---|---|---|
|As a bat, I want to reflect a randomly spawned ball|<ul><li>The ball is able to be reflected upon contact with the bat.</li><li>The ball can be spawned in a random location</li><li>The ball and the bat are able to move</li></ul>|Yes|Await result|<ul><li>Level 1</li><li>Will not release until requirement has been met</li></ul>

|User Story|Acceptance Test|Critical|Test Result|Comments|
|---|---|---|---|---|
|I need to hit the obstacles with the ball|<ul><li>Ball reflects when hitting a obstacle</li><li>Obstacles disappear when ball hits them</li></ul>|Yes|Await result|<ul><li>Level 1</li><li>Will not release until requirement has been met</li></ul>

|User Story|Acceptance Test|Critical|Test Result|Comments|
|---|---|---|---|---|
|I need to keep the ball in the frame|<ul><li>The ball reflects upon contact with the left, right and top edge</li><li>The ball disappears upon contact with bottom edge</li><li>The game restarts upon disappearance of ball</li></ul>|Yes|Await result|<ul><li>Level 1</li><li>Will not release until requirement has been met</li></ul>

|User Story|Acceptance Test|Critical|Test Result|Comments|
|---|---|---|---|---|
|In a non-adversial environment|<ul><li>The game ends when all the blocks are removed</li><li>Indicate that there is a first difficulty level</li><li>The game ends when all obstacles are clear</li></ul>|Yes|Await result|<ul><li>Level 1</li><li>Will not release until requirement has been met</li></ul>

### Second Sprint
|User Story|Acceptance Test|Critical|Test Result|Comments|
|---|---|---|---|---|
|Some obstacles have instant effects|<ul><li>Effects should be activated upon contact with ball</li><li>Obstacles with effects should have their own unique colors</li></ul>|No|Await result of sprint 1|<ul><li>Level 2</li></ul>

|User Story|Acceptance Test|Critical|Test Result|Comments|
|---|---|---|---|---|
|Some obstacles are more durable than others|<ul><li>Some obstacles should take more than one hit to break</li></ul>|No|Await result of sprint 1|<ul><li>Level 2</li></ul>

|User Story|Acceptance Test|Critical|Test Result|Comments|
|---|---|---|---|---|
|Some obstacles drop falling upgrades and may be destroyed by a ball|<ul><li>Falling upgrades should be generated upon destroying certain obstacles</li><li>Upgrades must be collected by contact with bat</li><li>Upgrades must be destroyed upon contact with ball</li></ul>|No|Await result of sprint 1|<ul><li>Level 2</li></ul>

|User Story|Acceptance Test|Critical|Test Result|Comments|
|---|---|---|---|---|
|Some obstacles drop falling downgrades and may be destroyed by a ball|<ul><li>Falling downgrades should be generated upon destroying certain obstacles</li><li>Downgrades must be collected by contact with bat</li><li>Downgrades must be destroyed upon contact with ball</li></ul>|No|Await result of sprint 1|<ul><li>Level 2</li></ul>

### Third Sprint
|User Story|Acceptance Test|Critical|Test Result|Comments|
|---|---|---|---|---|
|Generate balls within gaps between tiles|<ul><li>The ball should bounce within the gaps</li><li>Upon breaking the gaps, the ball could bounce out of the gap</li><li>Ball should be able to destroy obstacles upon contact with the bat</li></ul>|No|Await result of sprint 3|<ul><li>Level 3</li></ul>

|User Story|Acceptance Test|Critical|Test Result|Comments|
|---|---|---|---|---|
|Balls could be blinking|<ul><li>Ball should be drawn at every 2 frames</li></ul>|No|Await result of sprint 3|<ul><li>Level 3</li></ul>

|User Story|Acceptance Test|Critical|Test Result|Comments|
|---|---|---|---|---|
|There will be an invisible obstacle level|<ul><li>Obstacles should be drawn at random frames</li></ul>|No|Await result of sprint 3|<ul><li>Level 3</li></ul>

|User Story|Acceptance Test|Critical|Test Result|Comments|
|---|---|---|---|---|
|Gradually falling blocks|<ul><li>Obstacles should move one unit closer to players after some time at a constant rate</li></ul>|No|Await result of sprint 3|<ul><li>Level 3</li></ul>
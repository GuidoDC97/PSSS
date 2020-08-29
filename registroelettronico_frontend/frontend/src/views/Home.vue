<template>
  <div class="home">
<!--    <h1>Homepage</h1>-->

<!--    <v-btn class="pink white&#45;&#45;text">click me</v-btn>-->
<!--    <v-btn depressed color="pink">click me</v-btn>-->

<!--    <v-btn fab depressed large class="pink white&#45;&#45;text">-->
<!--      <v-icon large>mdi-dog</v-icon>-->
<!--    </v-btn>-->


<!--    <v-btn depressed class="pink white&#45;&#45;text">-->
<!--      <v-icon>mdi-check-all</v-icon>-->
<!--    </v-btn>-->

<!--    <v-btn depressed small class="pink white&#45;&#45;text">-->
<!--      <v-icon left small>mdi-email</v-icon>-->
<!--      <span>contact me</span>-->
<!--    </v-btn>-->


<!--TAB STUDENTE DOCENTE SEGRETERIA-->
    <v-card>
      <v-tabs
          v-model="tab"
          background-color="white"
          centered
          grow
          icons-and-text
      >
        <v-tabs-slider></v-tabs-slider>

        <v-tab href="#tab-1">
          Segreteria
          <v-icon>mdi-comment-question</v-icon>
        </v-tab>

        <v-tab href="#tab-2">
          Docente
          <v-icon>mdi-account-box</v-icon>
        </v-tab>

        <v-tab href="#tab-3">
          Studente
          <v-icon>mdi-face</v-icon>
        </v-tab>
      </v-tabs>
<!--      TAB SEGRETERIA-->
      <v-tabs-items v-model="tab">
        <v-tab-item :value="'tab-1'"
        >
          <v-app id="inspire">
            <v-row
                align="scratch"
                justify="center"
            >
              <v-col
                  cols="12"
                  sm="8"
                  md="4"
              >
                <v-card class="elevation-12">
                  <v-toolbar
                      color="primary"
                      dark
                  >
                    <v-toolbar-title>Login Segreteria</v-toolbar-title>
                    <v-spacer></v-spacer>
                  </v-toolbar>
                  <v-card-text>
                    <v-form>
                      <v-text-field
                          label="Login"
                          name="login"
                          prepend-icon="mdi-account"
                          type="text"
                      ></v-text-field>

                      <v-text-field
                          id="password"
                          label="Password"
                          name="password"
                          prepend-icon="mdi-lock"
                          type="password"
                      ></v-text-field>
                    </v-form>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="primary">Login</v-btn>
                  </v-card-actions>
                </v-card>
              </v-col>
            </v-row>
          </v-app>
        </v-tab-item>
<!--        TAB DOCENTE-->
        <v-tab-item :value="'tab-2'"
        >
          <v-app id="inspire">
            <v-row
                align="start"
                justify="center"
            >
              <v-col
                  cols="12"
                  sm="8"
                  md="4"
              >
                <v-card class="elevation-12">
                  <v-toolbar
                      color="primary"
                      dark
                  >
                    <v-toolbar-title>Login Docente</v-toolbar-title>
                    <v-spacer></v-spacer>
                  </v-toolbar>
                  <v-card-text>
                    <v-form>
                      <v-text-field
                          label="Login"
                          name="login"
                          prepend-icon="mdi-account"
                          type="text"
                      ></v-text-field>

                      <v-text-field
                          id="password"
                          label="Password"
                          name="password"
                          prepend-icon="mdi-lock"
                          type="password"
                      ></v-text-field>
                    </v-form>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="primary">Login</v-btn>
                  </v-card-actions>
                </v-card>
              </v-col>
            </v-row>
          </v-app>
        </v-tab-item>

<!--        TAB STUDENTE-->
        <v-tab-item :value="'tab-3'"
        >
          <v-app id="inspire">
            <v-row
                align="start"
                justify="center"
            >
              <v-col
                  cols="12"
                  sm="8"
                  md="4"
              >
                <v-card class="elevation-12">
                  <v-toolbar
                      color="primary"
                      dark
                  >
                    <v-toolbar-title>Login Studente</v-toolbar-title>
                    <v-spacer></v-spacer>
                  </v-toolbar>
                  <v-card-text>
                    <v-form>
                      <v-text-field
                          label="Login"
                          name="login"
                          prepend-icon="mdi-account"
                          type="text"
                      ></v-text-field>

                      <v-text-field
                          id="password"
                          label="Password"
                          name="password"
                          prepend-icon="mdi-lock"
                          type="password"
                      ></v-text-field>
                    </v-form>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="primary">Login</v-btn>
                  </v-card-actions>
                </v-card>
              </v-col>
            </v-row>
          </v-app>
        </v-tab-item>
      </v-tabs-items>
    </v-card>


<!--FORM LOGIN    -->
<!--    <v-app id="inspire">-->
<!--          <v-row-->
<!--              align="start"-->
<!--              justify="center"-->
<!--          >-->
<!--            <v-col-->
<!--                cols="12"-->
<!--                sm="8"-->
<!--                md="4"-->
<!--            >-->
<!--              <v-card class="elevation-12">-->
<!--                <v-toolbar-->
<!--                    color="primary"-->
<!--                    dark-->
<!--                >-->
<!--                  <v-toolbar-title>Login</v-toolbar-title>-->
<!--                  <v-spacer></v-spacer>-->
<!--                </v-toolbar>-->
<!--                <v-card-text>-->
<!--                  <v-form>-->
<!--                    <v-text-field-->
<!--                        label="Login"-->
<!--                        name="login"-->
<!--                        prepend-icon="mdi-account"-->
<!--                        type="text"-->
<!--                    ></v-text-field>-->

<!--                    <v-text-field-->
<!--                        id="password"-->
<!--                        label="Password"-->
<!--                        name="password"-->
<!--                        prepend-icon="mdi-lock"-->
<!--                        type="password"-->
<!--                    ></v-text-field>-->
<!--                  </v-form>-->
<!--                </v-card-text>-->
<!--                <v-card-actions>-->
<!--                  <v-spacer></v-spacer>-->
<!--                  <v-btn color="primary">Login</v-btn>-->
<!--                </v-card-actions>-->
<!--              </v-card>-->
<!--            </v-col>-->
<!--          </v-row>-->
<!--    </v-app>-->



  </div>
</template>



<script>
export default {
  data () {
    return {
      tab: null,
      text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
    }
  },
}
</script>

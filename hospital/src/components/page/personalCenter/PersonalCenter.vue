<template>
  <div class="personalCenter">
    <Row style="background:#eee;padding:20px">
      <Col span="11">
        <Card :bordered="false">
          <p slot="title">Borderless card</p>
          <p>Content of card</p>
          <p>Content of card</p>
          <p>Content of card</p>
        </Card>
      </Col>
      <Col span="11" offset="2">
        <Card shadow>
          <p slot="title">Use a card with a shadow effect</p>
          <p>Content of card</p>
          <p>Content of card</p>
          <p>Content of card</p>
        </Card>
      </Col>
    </Row>
  </div>
</template>

<script>
    export default {
        name: "PersonalCenter"
    }
</script>

<style scoped>

</style>
